package com.fernanortega.rickandmorty.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.fernanortega.rickandmorty.R
import com.fernanortega.rickandmorty.RickAndMortyApplication
import coil.ImageLoader
import androidx.compose.ui.tooling.preview.Preview

/**
 * Componente para mostrar una imagen a partir de una url con Coil, imagen que puede ser
 * cacheada por el [ImageLoader] en [RickAndMortyApplication]
 * @param modifier modificador para personalizar el componente
 * @param imageUrl url de la imagen a mostrar
 * @param contentDescription texto descriptivo de la imagen
 * @param crossFade si la imagen debe animarse al cargarla
 * @param contentScale escala del contenido de la imagen
 * @param placeHolder imagen a mostrar mientras se carga la otra, encuentra un error o si se encuentra en un [Preview]
 */
@Composable
fun DynamicImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String? = null,
    crossFade: Boolean = true,
    contentScale: ContentScale = ContentScale.Fit,
    @DrawableRes placeHolder: Int = R.drawable.placeholder_image
) {
    val inspectionMode = LocalInspectionMode.current
    if (inspectionMode) {
        Image(
            painter = painterResource(id = placeHolder),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    } else {
        AsyncImage(
            modifier = modifier,
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(crossFade)
                .fallback(placeHolder)
                .error(placeHolder)
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            imageLoader = LocalContext.current.imageLoader
        )
    }
}