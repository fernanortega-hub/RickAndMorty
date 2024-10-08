package com.fernanortega.rickandmorty.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
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
import com.fernanortega.rickandmorty.presentation.search.components.CharacterResults
import com.fernanortega.rickandmorty.presentation.search.components.NoCharactersFound
import com.fernanortega.rickandmorty.presentation.search.components.RecentSearches
import com.fernanortega.rickandmorty.ui.theme.RickAndMortyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchUiState,
    onBack: () -> Unit,
    onQueryChange: (String) -> Unit,
    onExplicitlySearch: (String) -> Unit,
    clearRecentSearches: () -> Unit,
    onCharacterClick: (Int) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
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
                        text = stringResource(id = R.string.search_characters)
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
                if (uiState.isSearching) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .imePadding(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    if (uiState.searchContent.characters.isEmpty() && uiState.searchQuery.length >= 2) {
                        NoCharactersFound(
                            query = uiState.searchQuery,
                            modifier = Modifier
                                .fillMaxWidth()
                                .imePadding()
                        )
                    }

                    if (uiState.searchContent.characters.isEmpty()) {
                        RecentSearches(
                            recentSearches = uiState.recentSearches,
                            clearRecentSearches = clearRecentSearches,
                            onSearch = onQueryChange,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    } else {
                        CharacterResults(
                            characters = uiState.searchContent.characters,
                            modifier = Modifier
                                .imePadding()
                                .fillMaxWidth(),
                            onCharacterClick = onCharacterClick
                        )
                    }
                }
            }

            if (uiState.isSearching) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (uiState.searchContent.characters.isEmpty() && uiState.searchQuery.length > 2) {
                    NoCharactersFound(
                        query = uiState.searchQuery,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                RecentSearches(
                    recentSearches = uiState.recentSearches,
                    clearRecentSearches = clearRecentSearches,
                    onSearch = onQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (uiState.searchContent.characters.isEmpty())
                                Modifier.weight(1f)
                            else Modifier.heightIn(min = 100.dp)
                        )
                )


                if (uiState.searchContent.characters.isNotEmpty()) {
                    CharacterResults(
                        characters = uiState.searchContent.characters,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        onCharacterClick = onCharacterClick
                    )
                }
            }


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
            clearRecentSearches = {},
            onCharacterClick = {}
        )
    }
}