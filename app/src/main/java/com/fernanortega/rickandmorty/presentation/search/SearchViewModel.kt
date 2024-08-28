package com.fernanortega.rickandmorty.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    val uiState: StateFlow<SearchUiState> = combine(_searchQuery) { searchQuery ->
        SearchUiState(searchQuery = searchQuery.joinToString(""))
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
        // TODO: Perform search and save search query
    }
}

private const val SEARCH_QUERY = "searchQuery"