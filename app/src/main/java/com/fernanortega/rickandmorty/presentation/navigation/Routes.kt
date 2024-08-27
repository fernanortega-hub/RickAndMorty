package com.fernanortega.rickandmorty.presentation.navigation

import androidx.navigation.NamedNavArgument

sealed class Routes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Feed: Routes("feed")
}