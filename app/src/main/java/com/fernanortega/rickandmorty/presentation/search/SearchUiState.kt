package com.fernanortega.rickandmorty.presentation.search

import com.fernanortega.rickandmorty.domain.model.RecentSearch
import com.fernanortega.rickandmorty.domain.model.SearchContent

data class SearchUiState(
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val recentSearches: List<RecentSearch> = emptyList(),
    val searchContent: SearchContent = SearchContent(
        characters = emptyList()
    ),
    val error: String? = null
)
