package com.fernanortega.rickandmorty.data.repository

import com.fernanortega.rickandmorty.domain.model.SearchContent
import kotlinx.coroutines.flow.Flow

interface SearchContentRepository {
    suspend fun getSearchContents(query: String): Result<SearchContent>
}