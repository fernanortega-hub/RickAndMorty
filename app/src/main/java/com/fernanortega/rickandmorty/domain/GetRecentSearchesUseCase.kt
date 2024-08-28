package com.fernanortega.rickandmorty.domain

import com.fernanortega.rickandmorty.data.repository.RecentSearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRecentSearchesUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository
) {
    operator fun invoke() = recentSearchRepository.getAllRecentSearches()
}