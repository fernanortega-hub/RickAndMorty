package com.fernanortega.rickandmorty.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fernanortega.rickandmorty.presentation.feed.feedRoute

@Composable
fun RickAndMortyNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = Routes.Feed.route
    ) {
        feedRoute()
    }
}