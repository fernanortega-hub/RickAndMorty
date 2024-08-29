package com.fernanortega.rickandmorty.presentation.characterdetails

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fernanortega.rickandmorty.presentation.navigation.Routes

const val CHARACTER_ID_KEY = "characterId"
const val CHARACTER_DEEP_LINK = "https://rickandmortyapi.com/api/character/{$CHARACTER_ID_KEY}"

fun NavGraphBuilder.characterDetailsRoute(
    onBackClick: () -> Unit
) {
    composable(
        route = Routes.CharacterDetails.route,
        arguments = Routes.CharacterDetails.arguments,
        deepLinks = Routes.CharacterDetails.deepLinks
    ) {
        val characterId = it.arguments?.getInt(CHARACTER_ID_KEY) ?: 0

        val viewModel: CharacterDetailsViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.updateCharacterId(characterId)
        }

        CharacterDetailsScreen(
            uiState = uiState,
            onBackClick = onBackClick
        )

    }
}