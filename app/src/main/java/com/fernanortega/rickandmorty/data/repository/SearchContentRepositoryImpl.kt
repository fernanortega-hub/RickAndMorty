package com.fernanortega.rickandmorty.data.repository

import com.fernanortega.rickandmorty.common.AppDispatchers
import com.fernanortega.rickandmorty.common.Dispatcher
import com.fernanortega.rickandmorty.data.database.dao.CharacterDao
import com.fernanortega.rickandmorty.data.network.services.character.CharacterService
import com.fernanortega.rickandmorty.domain.model.SearchContent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchContentRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val characterService: CharacterService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): SearchContentRepository {

    override suspend fun getSearchContents(query: String): Result<SearchContent> {
        return withContext(ioDispatcher) {
            try {
                val localData = characterDao.filterCharactersByName(query).toList()
                if (localData.isNotEmpty()) {
                    return@withContext Result.success(SearchContent(localData.map { it.toModel() }))
                }

                val characterResponse = characterService.filterCharactersByName(query)
                val results = characterResponse.body()?.results?.map {
                    characterDao.insertCharacter(
                        character = it.toRealmModel()
                    )
                }.orEmpty()

                Result.success(SearchContent(results.map { it.toModel() }))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
