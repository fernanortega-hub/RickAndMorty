package com.fernanortega.rickandmorty.data.network.services.character.response

import com.fernanortega.rickandmorty.data.network.model.NetworkCharacter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllCharacterResponse(
    @SerialName("results") val results: List<NetworkCharacter>?
)
