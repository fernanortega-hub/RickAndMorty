package com.fernanortega.rickandmorty.presentation.feed.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fernanortega.rickandmorty.R
import com.fernanortega.rickandmorty.domain.model.Character
import com.fernanortega.rickandmorty.domain.model.LocationCharacter
import com.fernanortega.rickandmorty.domain.model.OriginCharacter
import com.fernanortega.rickandmorty.presentation.components.DynamicImage
import com.fernanortega.rickandmorty.ui.modifiers.statusDot
import com.fernanortega.rickandmorty.ui.theme.RickAndMortyTheme
import com.fernanortega.rickandmorty.presentation.feed.FeedScreen

/**
 * Componente para mostrar una tarjeta de un personaje de Rick and Morty.
 * > Diseñada para ser reutilizable en diferentes partes de la interfaz de usuario.
 * > Pero su principal uso es en [FeedScreen].
 */
@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: Character,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            DynamicImage(
                imageUrl = character.image,
                contentDescription = stringResource(R.string.image_of_character, character.name),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )
            Text(
                text = character.status,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, bottom = 8.dp, end = 8.dp, start = 24.dp)
                    .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)
                    .statusDot(character.status),
                style = MaterialTheme.typography.labelSmall
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${character.species} - ${character.gender}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = stringResource(
                    R.string.origin_in_and_last_known_location_is,
                    character.name,
                    character.origin.name,
                    character.location.name
                )
            )

            Text(
                text = if (character.episode.size == 1) {
                    stringResource(R.string.has_appeared_in_episode, character.episode.size)
                } else {
                    stringResource(R.string.has_appeared_in_episodes, character.episode.size)
                }
            )
        }
    }
}

@Preview
@Composable
private fun CharacterCardPreview() {
    RickAndMortyTheme {
        CharacterCard(
            character = Character(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                origin = OriginCharacter(
                    name = "Earth",
                    url = ""
                ),
                location = LocationCharacter(
                    name = "Earth",
                    url = ""
                ),
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                episode = listOf(),
                url = ""
            ),
            onClick = {}
        )
    }
}