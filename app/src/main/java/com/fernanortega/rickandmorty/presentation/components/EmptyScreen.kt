package com.fernanortega.rickandmorty.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fernanortega.rickandmorty.R

/**
 * Muestra un mensaje de que no hay datos disponibles o personalizable según necesidad
 * @param modifier modificador para personalizar el componente
 * @param text texto a mostrar
 */
@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.no_data)
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}