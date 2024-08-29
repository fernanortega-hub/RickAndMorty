package com.fernanortega.rickandmorty.presentation.feed

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.fernanortega.rickandmorty.presentation.navigation.Routes

fun NavGraphBuilder.feedRoute(
    onSearchClick: () -> Unit,
    onCharacterClick: (Int) -> Unit
) {
    composable(Routes.Feed.route) {
        val viewModel: FeedViewModel = hiltViewModel()
        val charactersPaging  = viewModel.charactersPaging.collectAsLazyPagingItems()
        FeedScreen(
            charactersPaging = charactersPaging,
            onSearchClick = onSearchClick,
            onCharacterClick = onCharacterClick
        )
    }
}