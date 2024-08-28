package com.fernanortega.rickandmorty.data.network.model

import com.fernanortega.rickandmorty.data.database.schemas.RealmCharacter
import com.fernanortega.rickandmorty.data.database.schemas.RealmLocationCharacter
import com.fernanortega.rickandmorty.data.database.schemas.RealmOriginCharacter
import com.fernanortega.rickandmorty.domain.model.Character
import com.fernanortega.rickandmorty.domain.model.LocationCharacter
import com.fernanortega.rickandmorty.domain.model.OriginCharacter
import io.realm.kotlin.ext.toRealmList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCharacter(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("status") val status: String,
    @SerialName("species") val species: String,
    @SerialName("type") val type: String,
    @SerialName("gender") val gender: String,
    @SerialName("origin") val origin: NetworkOriginCharacter,
    @SerialName("location") val location: NetworkLocationCharacter,
    @SerialName("image") val image: String,
    @SerialName("episode") val episode: List<String>,
    @SerialName("url") val url: String
) {
    fun toModel(): Character = Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.toModel(),
        location = location.toModel(),
        image = image,
        episode = episode,
        url = url
    )

    fun toRealmModel(): RealmCharacter = RealmCharacter().apply {
        id = this@NetworkCharacter.id
        name = this@NetworkCharacter.name
        status = this@NetworkCharacter.status
        species = this@NetworkCharacter.species
        type = this@NetworkCharacter.type
        gender = this@NetworkCharacter.gender
        origin = RealmOriginCharacter().apply {
            name = this@NetworkCharacter.origin.name
            url = this@NetworkCharacter.origin.url
        }
        location = RealmLocationCharacter().apply {
            name = this@NetworkCharacter.location.name
            url = this@NetworkCharacter.location.url
        }
        image = this@NetworkCharacter.image
        episode = this@NetworkCharacter.episode.toRealmList()
        url = this@NetworkCharacter.url
    }
}

@Serializable
data class NetworkLocationCharacter(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
) {
    fun toModel(): LocationCharacter = LocationCharacter(
        name = name, url = url
    )
}

@Serializable
data class NetworkOriginCharacter(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
){
    fun toModel(): OriginCharacter = OriginCharacter(
        name = name, url = url
    )
}