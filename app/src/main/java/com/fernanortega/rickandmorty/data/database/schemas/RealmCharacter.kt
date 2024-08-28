package com.fernanortega.rickandmorty.data.database.schemas

import com.fernanortega.rickandmorty.data.model.Character
import com.fernanortega.rickandmorty.data.model.LocationCharacter
import com.fernanortega.rickandmorty.data.model.OriginCharacter
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmCharacter : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var status: String = ""
    var species: String = ""
    var type: String = ""
    var gender: String = ""
    var origin: RealmOriginCharacter? = null
    var location: RealmLocationCharacter? = null
    var image: String = ""
    var episode: RealmList<String> = realmListOf()
    var url: String = ""
    var inPage: Int = 0

    fun toModel(): Character = Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin?.toModel() ?: OriginCharacter("", ""),
        location = location?.toModel() ?: LocationCharacter("", ""),
        image = image,
        episode = episode,
        url = url
    )
}

class RealmOriginCharacter : EmbeddedRealmObject {
    fun toModel(): OriginCharacter = OriginCharacter(
        name = name,
        url = url
    )

    var name: String = ""
    var url: String = ""
}

class RealmLocationCharacter : EmbeddedRealmObject {
    var name: String = ""
    var url: String = ""

    fun toModel(): LocationCharacter = LocationCharacter(
        name = name,
        url = url
    )
}