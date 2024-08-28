package com.fernanortega.rickandmorty.data.repository

import android.util.Log
import com.fernanortega.rickandmorty.common.AppDispatchers
import com.fernanortega.rickandmorty.common.BackendException
import com.fernanortega.rickandmorty.common.Dispatcher
import com.fernanortega.rickandmorty.data.database.dao.CharacterDao
import com.fernanortega.rickandmorty.data.model.Character
import com.fernanortega.rickandmorty.data.network.services.character.CharacterService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : CharacterRepository {
    override fun getAllCharacters(): Flow<List<Character>> {
        return characterDao.getAllCharacters().map { result -> result.list.map { it.toModel() } }
    }

    override suspend fun refreshCharactersFromNetwork(): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val res = characterService.getAllCharacters()

                if (!res.isSuccessful) return@withContext Result.failure(BackendException(res.code()))

                res.body()?.results?.map {
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
}

