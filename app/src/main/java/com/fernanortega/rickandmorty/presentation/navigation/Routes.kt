package com.fernanortega.rickandmorty.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.fernanortega.rickandmorty.presentation.characterdetails.CHARACTER_ID_KEY

sealed class Routes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Feed: Routes("feed")

    data object Search: Routes("search")

    data object CharacterDetails: Routes(
        route ="characterDetails/{$CHARACTER_ID_KEY}",
        arguments = listOf(
            navArgument(CHARACTER_ID_KEY) {
                type = NavType.IntType
                nullable = false
            }
        )
    ) {
        fun createRoute(characterId: Int) = "characterDetails/$characterId"
    }
}