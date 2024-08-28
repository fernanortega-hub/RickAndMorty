package com.fernanortega.rickandmorty.data.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginCharacter,
    val location: LocationCharacter,
    val image: String,
    val episode: List<String>,
    val url: String
)

data class OriginCharacter(
    val name: String,
    val url: String
)
data class LocationCharacter(
    val name: String,
    val url: String
)
