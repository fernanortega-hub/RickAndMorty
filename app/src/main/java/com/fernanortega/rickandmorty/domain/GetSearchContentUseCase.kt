package com.fernanortega.rickandmorty.domain

import com.fernanortega.rickandmorty.data.repository.SearchContentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSearchContentUseCase @Inject constructor(
    private val searchContentRepository: SearchContentRepository
) {
    suspend operator fun invoke(query: String) = searchContentRepository.getSearchContents(query)
}
