package com.fernanortega.rickandmorty.presentation.util

import android.content.Context
import android.widget.Toast

/**
 * Muestra un mensaje toast en la pantalla.
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}