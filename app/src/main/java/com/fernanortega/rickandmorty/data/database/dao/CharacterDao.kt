package com.fernanortega.rickandmorty.data.database.dao

import com.fernanortega.rickandmorty.data.database.schemas.RealmCharacter
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterDao @Inject constructor(
    private val realm: Realm
) {
    suspend fun insertCharacter(character: RealmCharacter): RealmCharacter = realm.write {
        copyToRealm(character, UpdatePolicy.ALL)
    }

    fun filterCharactersByName(name: String): RealmResults<RealmCharacter> = realm.query<RealmCharacter>("name CONTAINS $0", name).find()

    fun getCharactersAtPage(page: Int): RealmResults<RealmCharacter> = realm.query<RealmCharacter>(
        "inPage == $0", page
    ).find()

    fun getCharacterById(id: Int): RealmCharacter? = realm.query<RealmCharacter>("id == $0", id).first().find()
}