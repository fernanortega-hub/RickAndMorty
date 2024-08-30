package com.fernanortega.rickandmorty.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.fernanortega.rickandmorty.presentation.characterdetails.characterDetailsRoute
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
        /*
        Se realizan extensiones de NavGraphBuilder para crear las rutas de navegación y mantener este archivo
        limpio de instancias de viewModel y ayuda a hacerlo más corto y legible
         */
        feedRoute(
            onSearchClick = navController::navigateToSearch,
            onCharacterClick = navController::navigateToCharacterDetails
        )
        searchRoute(
            onBackClick = navController::navigateUp,
            onCharacterClick = navController::navigateToCharacterDetails
        )
        characterDetailsRoute(
            onBackClick = navController::navigateUp
        )
    }
}
