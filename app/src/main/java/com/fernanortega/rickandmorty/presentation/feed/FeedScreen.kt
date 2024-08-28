package com.fernanortega.rickandmorty.presentation.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fernanortega.rickandmorty.R
import com.fernanortega.rickandmorty.presentation.components.EmptyScreen
import com.fernanortega.rickandmorty.presentation.components.PullToRefreshContainer
import com.fernanortega.rickandmorty.presentation.feed.components.CharacterCard

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    uiState: FeedUiState,
    onRefresh: () -> Unit
) {
    PullToRefreshContainer(
        modifier = modifier.fillMaxSize(),
        isRefreshing = uiState.isLoading,
        onRefresh = onRefresh
    ) {
        if (uiState.characters.isEmpty()) {
            EmptyScreen(
                text = stringResource(R.string.no_characters_found),
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyVerticalStaggeredGrid(
                modifier = modifier,
                contentPadding = PaddingValues(16.dp),
                columns = StaggeredGridCells.Adaptive(300.dp),
                verticalItemSpacing = 12.dp,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = uiState.characters,
                    key = { it.id }
                ) { character ->
                    CharacterCard(
                        modifier = Modifier
                            .fillMaxWidth(),
                        character = character
                    )
                }
            }
        }
    }
}