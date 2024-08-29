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
    fun onSearchQueryChange(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
        if (query.length >= 2) {
            searchJob?.cancel()
            viewModelScope.launch {
                _uiState.update { state -> state.copy(isSearching = true) }
                delay(250)
                getSearchContentUseCase(query)
                    .onSuccess {
                        _uiState.update { state -> state.copy(searchContent = it) }
                    }
                    .onFailure {
                        if (it is BackendException) {
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
            }.also {
                searchJob = it
                _uiState.update { state -> state.copy(isSearching = false) }
            }
        }
    }

    fun onExplicitlySearch(searchQuery: String) {
        onSearchQueryChange(searchQuery)
        viewModelScope.launch {
            insertRecentSearchUseCase(searchQuery)
        }
    }

    fun clearRecentSearches() {
        viewModelScope.launch {
            clearRecentSearchesUseCase()
        }
    }
}

private const val SEARCH_QUERY = "searchQuery"