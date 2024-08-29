package com.fernanortega.rickandmorty.presentation.search

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fernanortega.rickandmorty.presentation.navigation.Routes

fun NavGraphBuilder.searchRoute(
    onBackClick: () -> Unit,
    onCharacterClick: (Int) -> Unit
) {
    composable(Routes.Search.route) {
        val viewModel: SearchViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        SearchScreen(
            uiState = uiState,
            onBack = onBackClick,
            onQueryChange = viewModel::onSearchQueryChange,
            onExplicitlySearch = viewModel::onExplicitlySearch,
            clearRecentSearches = viewModel::clearRecentSearches,
            onCharacterClick = onCharacterClick
        )
    }
}