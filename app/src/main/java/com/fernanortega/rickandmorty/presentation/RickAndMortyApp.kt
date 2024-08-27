package com.fernanortega.rickandmorty.presentation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fernanortega.rickandmorty.presentation.navigation.RickAndMortyNavHost

@Composable
fun RickAndMortyApp(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) {
        RickAndMortyNavHost(
            modifier = Modifier
                .consumeWindowInsets(it)
                .padding(it)
        )
    }
}