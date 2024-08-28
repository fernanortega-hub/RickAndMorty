package com.fernanortega.rickandmorty.presentation.search

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fernanortega.rickandmorty.presentation.navigation.Routes

fun NavGraphBuilder.searchRoute(
    onBack: () -> Unit
) {
    composable(Routes.Search.route) {
        val viewModel: SearchViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        SearchScreen(
            uiState = uiState,
            onBack = onBack,
            onQueryChange = viewModel::onSearchQueryChange,
            onExplicitlySearch = viewModel::onExplicitlySearch,
        )
    }
}