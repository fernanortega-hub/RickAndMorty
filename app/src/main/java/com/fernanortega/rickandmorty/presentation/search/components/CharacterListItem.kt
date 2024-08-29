package com.fernanortega.rickandmorty.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fernanortega.rickandmorty.R
import com.fernanortega.rickandmorty.domain.model.Character
import com.fernanortega.rickandmorty.domain.model.LocationCharacter
import com.fernanortega.rickandmorty.domain.model.OriginCharacter
import com.fernanortega.rickandmorty.presentation.components.DynamicImage
import com.fernanortega.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun CharacterListItem(
    modifier: Modifier = Modifier,
    character: Character,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { 
            Text(
                text = character.name
            )
        },
        leadingContent = {
            DynamicImage(
                imageUrl = character.image,
                contentDescription = stringResource(R.string.image_of_character, character.name),
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        },
        supportingContent = {
            Text(
                text = stringResource(
                    R.string.origin_in_and_last_known_location_is,
                    character.name,
                    character.origin.name,
                    character.location.name
                )
            )
        },
        modifier = modifier.clickable(
            onClick = onClick
        )
    )
}

@Preview
@Composable
private fun CharacterListItemPreview() {
    RickAndMortyTheme {
        Surface {
            CharacterListItem(
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
}