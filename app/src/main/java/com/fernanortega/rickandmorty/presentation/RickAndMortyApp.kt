package com.fernanortega.rickandmorty.presentation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fernanortega.rickandmorty.R
import com.fernanortega.rickandmorty.presentation.navigation.RickAndMortyNavHost
import com.fernanortega.rickandmorty.presentation.navigation.Routes
import com.fernanortega.rickandmorty.presentation.navigation.navigateToSearch

@Composable
fun RickAndMortyApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            if (navBackStackEntry?.destination?.route?.contains(Routes.Search.route, ignoreCase = true) != true) {
                RickAndMortyTopAppBar(
                    actions = {
                        IconButton(
                            onClick = navController::navigateToSearch
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = stringResource(R.string.search_characters_locations_or_episodes),
                            )
                        }
                    }
                )
            }
        }
    ) {
        RickAndMortyNavHost(
            modifier = Modifier
                .consumeWindowInsets(it)
                .padding(it),
            navController = navController
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RickAndMortyTopAppBar(
    modifier: Modifier = Modifier,
    titleRes: Int = R.string.app_name,
    onNavigationClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = {
            onNavigationClick?.let {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        actions = actions
    )
}
