package com.fernanortega.rickandmorty.presentation.characterdetails

import com.fernanortega.rickandmorty.domain.model.Character

data class CharacterDetailsUiState(
    val character: Character? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
