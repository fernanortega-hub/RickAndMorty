package com.fernanortega.rickandmorty.data.repository

import com.fernanortega.rickandmorty.common.AppDispatchers
import com.fernanortega.rickandmorty.common.Dispatcher
import com.fernanortega.rickandmorty.data.database.dao.CharacterDao
import com.fernanortega.rickandmorty.domain.model.SearchContent
import com.fernanortega.rickandmorty.data.network.services.character.CharacterService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchContentRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val characterService: CharacterService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): SearchContentRepository {
    override fun getSearchContents(query: String): Flow<SearchContent> {
        val characterResults = characterDao.filterCharactersByName(
            name = query
        )
            .distinctUntilChanged()

        return characterResults.map { results ->
            SearchContent(
                characters = results.list.map { it.toModel() }
            )
        }
    }

    override suspend fun querySearchInNetwork(query: String): Result<Unit> = withContext(ioDispatcher) {
        try {
            val characterResponse = async {
                characterService.filterCharactersByName(query)
            }

            characterResponse.await().body()?.results?.map {
                characterDao.insertCharacter(
                    character = it.toRealmModel()
                )
            }

            return@withContext Result.success(Unit)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

}
