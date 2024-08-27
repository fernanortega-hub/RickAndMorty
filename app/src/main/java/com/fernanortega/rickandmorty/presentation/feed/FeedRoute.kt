package com.fernanortega.rickandmorty.presentation.feed

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fernanortega.rickandmorty.presentation.navigation.Routes

fun NavGraphBuilder.feedRoute() {
    composable(Routes.Feed.route) {
        val viewModel: FeedViewModel = hiltViewModel()
        val feedUiState by viewModel.uiState.collectAsStateWithLifecycle()

        FeedScreen(
            feedUiState = feedUiState
        )
    }
}

