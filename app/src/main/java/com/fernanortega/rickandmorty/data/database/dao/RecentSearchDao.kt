package com.fernanortega.rickandmorty.data.database.dao

import com.fernanortega.rickandmorty.data.database.schemas.RealmRecentSearch
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentSearchDao @Inject constructor(
    private val realm: Realm
) {
    suspend fun insertRecentSearch(recentSearch: RealmRecentSearch): RealmRecentSearch = realm.write {
        copyToRealm(recentSearch, UpdatePolicy.ALL)
    }
    fun getAllRecentSearches(): Flow<ResultsChange<RealmRecentSearch>> = realm.query<RealmRecentSearch>().asFlow()

    suspend fun deleteAllRecentSearches() = realm.write {
        delete(query<RealmRecentSearch>().find())
    }
}