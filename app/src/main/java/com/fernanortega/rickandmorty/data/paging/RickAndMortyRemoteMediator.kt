package com.fernanortega.rickandmorty.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.fernanortega.rickandmorty.data.database.dao.CharacterDao
import com.fernanortega.rickandmorty.data.network.services.character.CharacterService
import com.fernanortega.rickandmorty.domain.model.Character
import kotlin.math.ceil

@OptIn(ExperimentalPagingApi::class)
class RickAndMortyRemoteMediator(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao
) : RemoteMediator<Int, Character>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        // Al no tener información sobre la paginación actual de Paging, se puede calcular por el id
                        // entre el número de elementos que se agregan en la lista (20), ceil es para aproximarlo al
                        // entero mayor
                        ceil((lastItem.id.toFloat() / 20f)).toInt() + 1
                    }
                }
            }

            val networkCharacters = characterService.getCharactersAtPage(page)
            if (loadType == LoadType.REFRESH) {
                characterDao.deleteAll()
            }
            networkCharacters.body()?.results.orEmpty().forEach {
                characterDao.insertCharacter(
                    character = it.toRealmModel().apply {
                        inPage = page
                    }
                )
            }

            MediatorResult.Success(
                endOfPaginationReached = networkCharacters.body()?.info?.next == null
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}