package com.fernanortega.rickandmorty.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.fernanortega.rickandmorty.presentation.navigation.RickAndMortyNavHost

@Composable
fun RickAndMortyApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    RickAndMortyNavHost(
        modifier = modifier
            .fillMaxSize(),
        navController = navController
    )
}