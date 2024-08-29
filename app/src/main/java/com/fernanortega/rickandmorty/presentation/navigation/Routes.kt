package com.fernanortega.rickandmorty.presentation.navigation

import android.content.Intent
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.fernanortega.rickandmorty.presentation.characterdetails.CHARACTER_DEEP_LINK
import com.fernanortega.rickandmorty.presentation.characterdetails.CHARACTER_ID_KEY

sealed class Routes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val deepLinks: List<NavDeepLink> = emptyList()
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
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = CHARACTER_DEEP_LINK
                action = Intent.ACTION_VIEW
                mimeType = "text/*"
            }
        )
    ) {
        fun createRoute(characterId: Int) = "characterDetails/$characterId"
    }
}