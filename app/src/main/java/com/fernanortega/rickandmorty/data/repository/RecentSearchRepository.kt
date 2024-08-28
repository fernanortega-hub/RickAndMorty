package com.fernanortega.rickandmorty.data.repository

import com.fernanortega.rickandmorty.data.model.RecentSearch
import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {
    fun getAllRecentSearches(): Flow<List<RecentSearch>>
    suspend fun insertRecentSearch(query: String)
    suspend fun deleteAllRecentSearches()
}