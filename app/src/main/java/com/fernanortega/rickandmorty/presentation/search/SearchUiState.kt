package com.fernanortega.rickandmorty.presentation.search

import com.fernanortega.rickandmorty.data.model.Character

data class SearchUiState(
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val characters: List<Character> = emptyList(),
//    val locations: List<Location> = emptyList(),
//    val episodes: List<Episode> = emptyList()
)
