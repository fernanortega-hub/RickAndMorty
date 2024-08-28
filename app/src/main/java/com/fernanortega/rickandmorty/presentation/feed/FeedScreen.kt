package com.fernanortega.rickandmorty.presentation.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fernanortega.rickandmorty.presentation.components.PullToRefreshContainer

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
            Text(text = "No characters found")
        } else {
            val context = LocalContext.current
            LazyColumn(
                modifier = modifier
            ) {
                items(
                    uiState.characters,
                    key = { it.id }
                ) {
                    Column(
                        modifier = Modifier.fillParentMaxWidth()
                    ) {
                        Text(
                            text = it.name
                        )
                        AsyncImage(
                            model = ImageRequest.Builder(context).data(it.image).build(),
                            contentDescription = "Imagen de ${it.name}",
                            imageLoader = ImageLoader(context),
                            modifier = Modifier.width(300.dp)
                        )
                    }
                }
            }
        }
    }
}