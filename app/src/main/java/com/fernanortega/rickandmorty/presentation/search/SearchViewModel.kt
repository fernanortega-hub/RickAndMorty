package com.fernanortega.rickandmorty.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernanortega.rickandmorty.domain.ClearRecentSearchesUseCase
import com.fernanortega.rickandmorty.domain.GetRecentSearchesUseCase
import com.fernanortega.rickandmorty.domain.InsertRecentSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getRecentSearchesUseCase: GetRecentSearchesUseCase,
    private val insertRecentSearchUseCase: InsertRecentSearchUseCase,
    private val clearRecentSearchesUseCase: ClearRecentSearchesUseCase
): ViewModel() {
    private val _searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    val uiState: StateFlow<SearchUiState> = combine(_searchQuery, getRecentSearchesUseCase()) { searchQuery, recentSearches ->
        SearchUiState(
            searchQuery = searchQuery,
            recentSearches = recentSearches
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchUiState()
    )

    fun onSearchQueryChange(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    fun onExplicitlySearch(searchQuery: String) {
        savedStateHandle[SEARCH_QUERY] = searchQuery
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