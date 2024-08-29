package com.fernanortega.rickandmorty.presentation.characterdetails

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fernanortega.rickandmorty.R
import com.fernanortega.rickandmorty.domain.model.Character
import com.fernanortega.rickandmorty.domain.model.LocationCharacter
import com.fernanortega.rickandmorty.domain.model.OriginCharacter
import com.fernanortega.rickandmorty.presentation.components.DynamicImage
import com.fernanortega.rickandmorty.presentation.components.EmptyScreen
import com.fernanortega.rickandmorty.presentation.components.RickAndMortyTopAppBar
import com.fernanortega.rickandmorty.ui.modifiers.statusDot
import com.fernanortega.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun CharacterDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: CharacterDetailsUiState,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            RickAndMortyTopAppBar(
                title = uiState.character?.name.orEmpty(),
                onNavigationClick = onBackClick
            )
        }
    ) { innerPadding ->
        if (uiState.character == null) {
            EmptyScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding),
                text = uiState.error.orEmpty()
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 325.dp)
                ) {
                    DynamicImage(
                        imageUrl = uiState.character.image,
                        contentDescription = stringResource(
                            R.string.image_of_character,
                            uiState.character.name
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                MaterialTheme.shapes.large.copy(
                                    topStart = CornerSize(0.dp),
                                    topEnd = CornerSize(0.dp),
                                )
                            ),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = uiState.character.status,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 8.dp, bottom = 8.dp, end = 8.dp, start = 24.dp)
                            .background(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.shapes.small
                            )
                            .statusDot(uiState.character.status),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    val description = buildAnnotatedString {
                        val presentation =
                            stringResource(R.string.this_character_is, uiState.character.name)
                        val indexOfName = presentation.indexOf(uiState.character.name)
                        append(presentation)
                        addStyle(
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium
                            ).toSpanStyle(),
                            start = indexOfName,
                            end = presentation.length
                        )

                        append(", ")
                        val whoIs = if (uiState.character.type.isNotBlank()) {
                            stringResource(
                                R.string.a_type_who_is_currently_status,
                                uiState.character.type,
                                uiState.character.status
                            )
                        } else {
                            stringResource(
                                R.string.who_is_currently_status,
                                uiState.character.status
                            ).lowercase()
                        }

                        append(whoIs)
                        append("\n\n")

                        val firstName = uiState.character.name.split(' ').first()

                        val genderTextWithSpecies = when (uiState.character.gender) {
                            "Female" -> stringResource(
                                R.string.is_known_for_being_a_of_the_species,
                                firstName,
                                stringResource(R.string.female).lowercase(),
                                uiState.character.species.lowercase()
                            )

                            "Male" -> stringResource(
                                R.string.is_known_for_being_a_of_the_species,
                                firstName,
                                stringResource(R.string.male).lowercase(),
                                uiState.character.species.lowercase()
                            )

                            "Genderless" -> stringResource(
                                R.string.is_known_for_being_a_genderless_species,
                                firstName,
                                uiState.character.species.lowercase()
                            )

                            else -> stringResource(
                                R.string.gender_is_unknown_of_the_species,
                                firstName,
                                uiState.character.species.lowercase()
                            )
                        }
                        append(genderTextWithSpecies)
                        append(' ')
                        val hasAppearedInEpisodeOrEpisodes =
                            if (uiState.character.episode.size == 1) {
                                stringResource(
                                    R.string.has_appeared_in_episode,
                                    uiState.character.episode.size
                                )
                            } else {
                                stringResource(
                                    R.string.has_appeared_in_episodes,
                                    uiState.character.episode.size
                                )
                            }
                        append(hasAppearedInEpisodeOrEpisodes)
                        append(".\n\n")
                        append(
                            stringResource(
                                R.string.origin_in_and_last_known_location_is,
                                uiState.character.name,
                                uiState.character.origin.name,
                                uiState.character.location.name
                            )
                        )
                    }

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    val context = LocalContext.current
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, uiState.character.url)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(intent, null)

                            context.startActivity(shareIntent)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = null,
                            modifier = Modifier
                                .size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.share_character)
                        )
                    }
                }

            }
        }

    }
}

@Preview
@Composable
private fun CharacterDetailsScreenPreview() {
    RickAndMortyTheme {
        CharacterDetailsScreen(
            uiState = CharacterDetailsUiState(
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
                    image = "",
                    episode = emptyList(),
                    url = "",
                )
            ),
            onBackClick = {}
        )
    }
}
