package com.fernanortega.rickandmorty.presentation.feed

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fernanortega.rickandmorty.presentation.navigation.Routes
import com.fernanortega.rickandmorty.presentation.util.showToast

fun NavGraphBuilder.feedRoute() {
    composable(Routes.Feed.route) {
        val viewModel: FeedViewModel = hiltViewModel()
        val feedUiState by viewModel.uiState.collectAsStateWithLifecycle()
        val context = LocalContext.current

        LaunchedEffect(feedUiState.error) {
            if (feedUiState.error != null) {
                context.showToast(feedUiState.error!!)
            }
        }

        FeedScreen(
            uiState = feedUiState,
            onRefresh = viewModel::refreshCharacters
        )
    }
}