package com.fernanortega.rickandmorty.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernanortega.rickandmorty.common.BackendException
import com.fernanortega.rickandmorty.domain.ClearRecentSearchesUseCase
import com.fernanortega.rickandmorty.domain.GetRecentSearchesUseCase
import com.fernanortega.rickandmorty.domain.GetSearchContentUseCase
import com.fernanortega.rickandmorty.domain.InsertRecentSearchUseCase
import com.fernanortega.rickandmorty.domain.model.SearchContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getRecentSearchesUseCase: GetRecentSearchesUseCase,
    private val insertRecentSearchUseCase: InsertRecentSearchUseCase,
    private val clearRecentSearchesUseCase: ClearRecentSearchesUseCase,
    private val getSearchContentUseCase: GetSearchContentUseCase
) : ViewModel() {

    private val _searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = combine(
        _uiState,
        _searchQuery,
        getRecentSearchesUseCase()
    ) { uiState, searchQuery, recentSearches ->
        uiState.copy(
            searchQuery = searchQuery,
            recentSearches = recentSearches
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = _uiState.value
        )


    private var searchJob: Job? = null

    /**
     * Función que realizará la búsqueda de los personajes con el [query] dado
     */
    fun onSearchQueryChange(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
        // Si la query es menor a 2 caracteres, no se hace la busqueda para evitar posibles requests innecesarias
        if (query.length >= 2) {
            // Si se tiene un job en curso, se cancela para cancelar la busqueda anterior
            searchJob?.cancel()
            viewModelScope.launch {
                _uiState.update { state -> state.copy(isSearching = true) }
                // delay que funciona como debounce para no hacer una busqueda cada vez que se escribe un caracter
                delay(250)
                getSearchContentUseCase(query)
                    .onSuccess {
                        _uiState.update { state -> state.copy(searchContent = it) }
                    }
                    .onFailure {
                        if (it is BackendException) {
                            // En el caso de esta excepción, se muestra un mensaje al usuario y se actualiza la lista con una vacia
                            _uiState.update { state ->
                                state.copy(
                                    searchContent = SearchContent(
                                        emptyList()
                                    )
                                )
                            }
                        }
                        _uiState.update { state -> state.copy(error = it.message) }
                    }
                _uiState.update { state -> state.copy(isSearching = false) }
            }.also {
                searchJob = it
            }

        }
    }

    /**
     * Esta función guardará la [searchQuery] en la base de datos y luego la usará [onSearchQueryChange]
     */
    fun onExplicitlySearch(searchQuery: String) {
        onSearchQueryChange(searchQuery)
        viewModelScope.launch {
            insertRecentSearchUseCase(searchQuery)
        }
    }

    /**
     * Elimina todas las búsquedas recientes de la base de datos
     */
    fun clearRecentSearches() {
        viewModelScope.launch {
            clearRecentSearchesUseCase()
        }
    }
}

private const val SEARCH_QUERY = "searchQuery"