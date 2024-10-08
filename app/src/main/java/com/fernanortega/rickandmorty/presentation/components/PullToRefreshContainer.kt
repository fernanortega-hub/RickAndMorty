package com.fernanortega.rickandmorty.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer as M3PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

/**
 * Contenedor para uso de funcionalidad de pull to refresh donde sea necesario
 * @param modifier modificador para el contenedor
 * @param isRefreshing indica si se está realizando el refresh
 * @param onRefresh acción a realizar cuando se realiza el refresh
 * @param content contenido a mostrar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshContainer(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            onRefresh()
        }
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            pullToRefreshState.startRefresh()
        } else {
            pullToRefreshState.endRefresh()
        }
    }

    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        content()
        M3PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}