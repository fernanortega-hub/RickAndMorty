package com.fernanortega.rickandmorty.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Dibuja un punto con un color según el estado del personaje:
 * *Alive -> verde*,
 * *Dead -> rojo*,
 * *unknown -> gris*
 */
fun Modifier.statusDot(status: String): Modifier = this then drawWithContent {
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