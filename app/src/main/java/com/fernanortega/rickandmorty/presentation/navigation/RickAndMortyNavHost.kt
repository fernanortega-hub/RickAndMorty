package com.fernanortega.rickandmorty.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.fernanortega.rickandmorty.presentation.feed.feedRoute
import com.fernanortega.rickandmorty.presentation.search.searchRoute

@Composable
fun RickAndMortyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = Routes.Feed.route
    ) {
        feedRoute()
        searchRoute(
            onBack = navController::navigateUp
        )
    }
}
