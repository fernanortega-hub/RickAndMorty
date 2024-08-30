package com.fernanortega.rickandmorty.presentation.search.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fernanortega.rickandmorty.R

/**
 * Muestra un mensaje de que no se encontraron personajes con el [query] dado.
 */
@Composable
fun NoCharactersFound(
    modifier: Modifier = Modifier,
    query: String
) {
    val annotatedString = buildAnnotatedString {
        val queryMessage = "\"$query\""
        val fullMessage = (stringResource(id = R.string.no_characters_found) +
                " " + stringResource(R.string.for_you_search) + " ").lowercase()
            .replaceFirstChar { it.uppercase() } + queryMessage
        append(fullMessage)

        val queryStartIndex = fullMessage.indexOf(query)
        val queryEndIndex = queryStartIndex + query.length
        addStyle(
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ).toSpanStyle(),
            start = queryStartIndex,
            end = queryEndIndex
        )
    }

    Text(
        text = annotatedString,
        modifier = modifier
            .heightIn(min = 100.dp)
            .padding(16.dp),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )
}