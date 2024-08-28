package com.fernanortega.rickandmorty.presentation.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToSearch() {
    navigate(Routes.Search.route)
}