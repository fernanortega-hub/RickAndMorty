package com.fernanortega.rickandmorty.presentation.feed

import com.fernanortega.rickandmorty.data.model.Character

//sealed interface FeedUiState {
//    data object Loading: FeedUiState
//    data class Success(
//        val characters: List<Character>
//    ): FeedUiState
//
//    data object Empty: FeedUiState
//}

data class FeedUiState(
    val characters: List<Character>
)