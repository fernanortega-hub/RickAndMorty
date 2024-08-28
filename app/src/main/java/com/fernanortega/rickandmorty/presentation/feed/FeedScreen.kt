package com.fernanortega.rickandmorty.presentation.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.fernanortega.rickandmorty.R
import com.fernanortega.rickandmorty.domain.model.Character
import com.fernanortega.rickandmorty.presentation.components.EmptyScreen
import com.fernanortega.rickandmorty.presentation.components.PullToRefreshContainer
import com.fernanortega.rickandmorty.presentation.feed.components.CharacterCard
import com.fernanortega.rickandmorty.presentation.util.showToast

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    charactersPaging: LazyPagingItems<Character>
) {
    LoadStateError(charactersPaging)
    PullToRefreshContainer(
        modifier = modifier.fillMaxSize(),
        isRefreshing = charactersPaging.loadState.refresh is LoadState.Loading,
        onRefresh = { charactersPaging.refresh() }
    ) {
        if (charactersPaging.itemCount == 0 && charactersPaging.loadState.refresh is LoadState.NotLoading) {
            EmptyScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                text = stringResource(id = R.string.no_characters_found),
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyVerticalStaggeredGrid(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    columns = StaggeredGridCells.Adaptive(300.dp),
                    verticalItemSpacing = 12.dp,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(charactersPaging.itemCount) { index ->
                        charactersPaging[index]?.let {
                            CharacterCard(
                                character = it
                            )
                        }
                    }
                }

                if (charactersPaging.loadState.append is LoadState.Loading) {
                    LinearProgressIndicator()
                    Text(
                        text = stringResource(id = R.string.loading)
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadStateError(charactersPaging: LazyPagingItems<Character>) {
    val context = LocalContext.current
    LaunchedEffect(
        charactersPaging.loadState.refresh,
        charactersPaging.loadState.append,
        charactersPaging.loadState.prepend
    ) {
        val message = when {
            charactersPaging.loadState.refresh is LoadState.Error ->
                (charactersPaging.loadState.refresh as LoadState.Error).error.message.orEmpty()

            charactersPaging.loadState.append is LoadState.Error ->
                (charactersPaging.loadState.append as LoadState.Error).error.message.orEmpty()

            charactersPaging.loadState.prepend is LoadState.Error ->
                (charactersPaging.loadState.prepend as LoadState.Error).error.message.orEmpty()

            else -> null
        }
        message?.let { context.showToast(it) }
    }
}