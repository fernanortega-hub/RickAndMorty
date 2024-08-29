package com.fernanortega.rickandmorty.presentation.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToSearch() {
    navigate(Routes.Search.route)
}

fun NavHostController.navigateToCharacterDetails(characterId: Int) {
    navigate(Routes.CharacterDetails.createRoute(characterId))
}