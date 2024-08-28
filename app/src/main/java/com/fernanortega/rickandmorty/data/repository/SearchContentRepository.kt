package com.fernanortega.rickandmorty.data.repository

import com.fernanortega.rickandmorty.data.model.SearchContent
import kotlinx.coroutines.flow.Flow

interface SearchContentRepository {
    fun getSearchContents(query: String): Flow<SearchContent>
    suspend fun querySearchInNetwork(query: String): Result<Unit>
}