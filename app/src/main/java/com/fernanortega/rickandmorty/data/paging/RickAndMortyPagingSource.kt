package com.fernanortega.rickandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fernanortega.rickandmorty.common.BackendException
import com.fernanortega.rickandmorty.data.database.dao.CharacterDao
import com.fernanortega.rickandmorty.data.model.Character
import com.fernanortega.rickandmorty.data.network.services.character.CharacterService

class RickAndMortyPagingSource(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: 1
            val localResults = characterDao.getCharactersAtPage(page).toList()

            // Validar si existen resultados en la página actual en local, para no cargar desde backend
            if (localResults.isNotEmpty()) {
                val results = localResults.map { it.toModel() }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (results.isEmpty()) null else page + 1

                return LoadResult.Page(data = results, prevKey = prevKey, nextKey = nextKey)
            }

            // Si no existen resultados en local de la página actual, cargar desde backend
            val response = characterService.getCharactersAtPage(page = page)
            if (!response.isSuccessful) return LoadResult.Error(BackendException(response.code()))

            val results = response.body()?.results?.map {
                characterDao.insertCharacter(it.toRealmModel().apply { inPage = page })
            }.orEmpty()

            val next = response.body()?.info?.next

            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (next != null) page + 1 else null
            LoadResult.Page(
                data = results.map { it.toModel() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}