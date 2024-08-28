package com.fernanortega.rickandmorty.data.database.dao

import com.fernanortega.rickandmorty.data.database.schemas.RealmCharacter
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterDao @Inject constructor(
    private val realm: Realm
) {
    suspend fun insertCharacter(character: RealmCharacter): RealmCharacter = realm.write {
        copyToRealm(character, UpdatePolicy.ALL)
    }

    fun getAllCharacters(): Flow<ResultsChange<RealmCharacter>> = realm.query<RealmCharacter>().asFlow()
}