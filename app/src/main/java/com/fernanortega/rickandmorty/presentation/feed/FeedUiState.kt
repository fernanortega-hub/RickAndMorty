package com.fernanortega.rickandmorty.presentation.feed

import com.fernanortega.rickandmorty.data.model.Character

data class FeedUiState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val error: String? = null
)
