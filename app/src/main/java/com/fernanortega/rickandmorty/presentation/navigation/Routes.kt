package com.fernanortega.rickandmorty.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.fernanortega.rickandmorty.presentation.characterdetails.CHARACTER_ID_KEY

/**
 * Lista de rutas de navegación de la aplicación.
 * Cada subclase u objeto representa una ruta de navegación, con su respectiva ruta y argumentos.
 *
 * @property route ruta de navegación (única)
 * @property arguments posibles argumentos de navegación
 */
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
        /**
         * Crea la ruta de navegación para el detalle de un personaje.
         */
        fun createRoute(characterId: Int) = "characterDetails/$characterId"
    }
}