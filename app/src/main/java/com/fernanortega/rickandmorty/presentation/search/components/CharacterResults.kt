package com.fernanortega.rickandmorty.presentation.search.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fernanortega.rickandmorty.R
import com.fernanortega.rickandmorty.domain.model.Character

/**
 * Composable para mostrar los resultados de búsqueda de personajes.
 * > Nota: No maneja una lista vacia
 * @param modifier modificador para personalizar el diseño
 * @param characters lista de personajes a mostrar
 * @param onCharacterClick acción a realizar al hacer clic en un personaje
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterResults(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    onCharacterClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        stickyHeader(
            key = "characters_results"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(id = R.string.characters_results)
                )
            }
        }

        itemsIndexed(
            items = characters,
            key = { _, character -> character.id }
        ) { index, character ->
            CharacterListItem(
                character = character,
                onClick = { onCharacterClick(character.id) }
            )
            if (index != characters.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}