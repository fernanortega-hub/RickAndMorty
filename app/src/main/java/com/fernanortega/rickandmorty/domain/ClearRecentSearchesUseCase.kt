package com.fernanortega.rickandmorty.domain

import com.fernanortega.rickandmorty.data.repository.RecentSearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearRecentSearchesUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository
) {
    suspend operator fun invoke() = recentSearchRepository.deleteAllRecentSearches()
}