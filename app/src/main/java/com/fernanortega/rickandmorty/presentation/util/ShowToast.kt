package com.fernanortega.rickandmorty.presentation.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(toast: String) {
    Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
}