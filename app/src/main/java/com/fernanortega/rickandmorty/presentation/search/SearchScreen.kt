package com.fernanortega.rickandmorty.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
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
import androidx.compose.ui.unit.dp
import com.fernanortega.rickandmorty.R
import com.fernanortega.rickandmorty.domain.model.Character
import com.fernanortega.rickandmorty.presentation.feed.components.CharacterCard
import com.fernanortega.rickandmorty.presentation.search.components.RecentSearches
import com.fernanortega.rickandmorty.ui.theme.RickAndMortyTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchUiState,
    onBack: () -> Unit,
    onQueryChange: (String) -> Unit,
    onExplicitlySearch: (String) -> Unit,
    clearRecentSearches: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
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
            if (uiState.searchContent.characters.isEmpty()) {
                RecentSearches(
                    recentSearches = uiState.recentSearches,
                    clearRecentSearches = clearRecentSearches,
                    onSearch = onQueryChange,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .imePadding()
                )
            } else {
                CharacterResults(
                    characters = uiState.searchContent.characters,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .imePadding()
                )
            }
        }

        if (!active && uiState.searchContent.characters.isEmpty()) {
            RecentSearches(
                recentSearches = uiState.recentSearches,
                clearRecentSearches = clearRecentSearches,
                onSearch = onQueryChange,
                modifier = Modifier
                    .weight(1f)
            )
        }

        if (uiState.searchContent.characters.isNotEmpty()) {
            CharacterResults(
                characters = uiState.searchContent.characters,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .imePadding()
            )
        }
    }
}

@Composable
fun CharacterResults(
    modifier: Modifier = Modifier,
    characters: List<Character>
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        columns = StaggeredGridCells.Adaptive(300.dp),
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = characters,
            key = { character -> character.id }
        ) { character ->
            CharacterCard(
                character = character
            )
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
            onExplicitlySearch = {},
            clearRecentSearches = {}
        )
    }
}