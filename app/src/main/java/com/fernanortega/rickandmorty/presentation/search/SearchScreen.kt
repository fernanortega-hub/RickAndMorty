package com.fernanortega.rickandmorty.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fernanortega.rickandmorty.R
import com.fernanortega.rickandmorty.ui.theme.RickAndMortyTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchUiState,
    onBack: () -> Unit,
    onQueryChange: (String) -> Unit,
    onExplicitlySearch: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        var active by remember { mutableStateOf(false) }
        val onActiveChange = remember {
            {
                active = !active
            }
        }

        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = onQueryChange,
            onSearch = onExplicitlySearch,
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_characters_locations_or_episodes),
                    modifier = Modifier.basicMarquee()
                )
            },
            trailingIcon = {
                if (uiState.searchQuery.isNotBlank()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = stringResource(id = R.string.clear_search)
                        )
                    }
                }
            },
            leadingIcon = {
                IconButton(onClick = if (!active) onBack else onActiveChange) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = stringResource(
                            id =
                            if (!active) R.string.back else R.string.deactivate_search
                        )
                    )
                }
            }
        ) {

        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    RickAndMortyTheme {
        SearchScreen(
            uiState = SearchUiState(
                searchQuery = "Rick"
            ),
            onBack = {},
            onQueryChange = {},
            onExplicitlySearch = {}
        )
    }
}