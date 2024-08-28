package com.fernanortega.rickandmorty.presentation.search.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fernanortega.rickandmorty.R
import com.fernanortega.rickandmorty.data.model.RecentSearch
import com.fernanortega.rickandmorty.presentation.components.EmptyScreen
import com.fernanortega.rickandmorty.ui.theme.RickAndMortyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecentSearches(
    modifier: Modifier = Modifier,
    recentSearches: List<RecentSearch>,
    clearRecentSearches: () -> Unit,
    onSearch: (String) -> Unit
) {
    if (recentSearches.isEmpty()) {
        EmptyScreen(
            modifier = modifier,
            text = stringResource(id = R.string.no_recent_searches)
        )
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            stickyHeader(
                key = "recent_searches"
            ) {
                Row(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.recent_searches),
                    )

                    IconButton(
                        onClick = clearRecentSearches
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = stringResource(id = R.string.clear_recent_searches)
                        )
                    }
                }
            }
            itemsIndexed(
                items = recentSearches,
                key = { _, recentSearch -> recentSearch.query }
            ) { index, recentSearch ->
                ListItem(
                    modifier = Modifier
                        .animateItemPlacement()
                        .fillParentMaxWidth()
                        .clickable(
                            onClick = {
                                onSearch(recentSearch.query)
                            },
                            onClickLabel = stringResource(R.string.search_query, recentSearch.query),
                            role = Role.Button
                        ),
                    headlineContent = {
                        Text(text = recentSearch.query)
                    }
                )
                if (recentSearches.lastIndex != index) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview
@Composable
private fun PopulatedRecentSearchesPreview() {
    RickAndMortyTheme {
        RecentSearches(
            recentSearches = listOf(
                RecentSearch("Rick", 0)
            ),
            clearRecentSearches = {},
            onSearch = {}
        )
    }
}

@Preview
@Composable
private fun EmptyRecentSearchesPreview() {
    RickAndMortyTheme {
        RecentSearches(
            recentSearches = emptyList(),
            clearRecentSearches = {},
            onSearch = {}
        )
    }
}