package com.fernanortega.rickandmorty.domain

import com.fernanortega.rickandmorty.data.repository.RecentSearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertRecentSearchUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository
) {
    suspend operator fun invoke(query: String) = recentSearchRepository.insertRecentSearch(query)
}