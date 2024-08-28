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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fernanortega.rickandmorty.R
import com.fernanortega.rickandmorty.data.model.Character
import com.fernanortega.rickandmorty.data.model.LocationCharacter
import com.fernanortega.rickandmorty.data.model.OriginCharacter
import com.fernanortega.rickandmorty.presentation.components.DynamicImage
import com.fernanortega.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: Character
) {
    OutlinedCard(
        modifier = modifier
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
                    .padding(top = 12.dp, bottom = 12.dp, end = 12.dp, start = 32.dp)
                    .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)
                    .statusDot(character.status)
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
                    R.string.first_appeared_in_and_last_known_location_is,
                    character.name,
                    character.origin.name,
                    character.location.name
                )
            )
            val episodesText = buildString {
                append(
                    when (character.gender) {
                        "Male" -> stringResource(R.string.he)
                        "Female" -> stringResource(R.string.she)
                        else -> ""
                    }
                )
                append(" ${stringResource(R.string.appears_in_episodes, character.episode.size)}")
            }

            Text(
                text = episodesText.trim().replaceFirstChar { it.uppercase() }
            )
        }
    }
}

private fun Modifier.statusDot(status: String): Modifier = this then drawWithContent {
    drawContent()
    drawCircle(
        color = when (status) {
            "Alive" -> Color(0xFF19F500)
            "Dead" -> Color(0xFFFE4A49)
            else -> Color.Gray
        },
        radius = size.minDimension / 4,
        center = Offset(
            x = -12.dp.toPx(),
            y = size.height / 2
        )
    )
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
                url = "",
                created = "2017-11-04T18:48:46.250Z"
            ),
        )
    }
}