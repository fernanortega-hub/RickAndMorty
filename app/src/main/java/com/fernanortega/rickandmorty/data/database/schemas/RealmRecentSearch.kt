package com.fernanortega.rickandmorty.data.database.schemas

import com.fernanortega.rickandmorty.domain.model.RecentSearch
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmRecentSearch: RealmObject {
    fun toModel(): RecentSearch = RecentSearch(
        query = query,
        timestamp = timestamp.epochSeconds
    )

    @PrimaryKey
    var query: String = ""
    var timestamp: RealmInstant = RealmInstant.now()
}