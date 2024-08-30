package com.fernanortega.rickandmorty.presentation.navigation

import androidx.navigation.NavHostController

/*
    Extensiones para navegar a las pantallas de la aplicación, simplificando el código.
*/

fun NavHostController.navigateToSearch() {
    navigate(Routes.Search.route)
}

fun NavHostController.navigateToCharacterDetails(characterId: Int) {
    navigate(Routes.CharacterDetails.createRoute(characterId))
}