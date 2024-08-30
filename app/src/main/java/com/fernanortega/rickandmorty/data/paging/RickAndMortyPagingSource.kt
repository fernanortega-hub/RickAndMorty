package com.fernanortega.rickandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fernanortega.rickandmorty.data.database.dao.CharacterDao
import com.fernanortega.rickandmorty.data.network.services.character.CharacterService
import com.fernanortega.rickandmorty.domain.model.Character

class RickAndMortyPagingSource(
    private val characterDao: CharacterDao,
    private val characterService: CharacterService,
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

            // Si el state es Refresh dejar pasar directamente a network, para actualizar la base de datos local
            // y al mismo tiempo no se interfiere si el usuario hace pull to refresh para igualmente actualizar los datos
            val isRefresh = params is LoadParams.Refresh
            if (localResults.isNotEmpty() && !isRefresh) {
                val prevKey = if (page > 1) page - 1 else null
                return LoadResult.Page(data = localResults.map { it.toModel() }, prevKey = prevKey, nextKey = page + 1)
            }

            // A partir de este punto se decide borrar todos los personajes de la base de datos local
            // para actualizarlos con los nuevos datos de la api
            if (isRefresh) characterDao.deleteAll()

            val networkCharacters = characterService.getCharactersAtPage(page)

            // En algunos casos, al actualizar la base de datos local, no se guardaban algunos de los primeros resultados
            // ya cargados tanto en ui como en la base de datos local, para ello se mantienen los localResults obtenidos
            // anteriormente y se hace el distinctBy para que no se repitan los personajes al momento de hacer
            // scroll
            val newResults = (networkCharacters.body()?.results.orEmpty().map {
                characterDao.insertCharacter(
                    character = it.toRealmModel().apply {
                        inPage = page
                    }
                )
            } + localResults).distinctBy { it.id }
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (newResults.isEmpty()) null else page + 1

            return LoadResult.Page(data = newResults.map { it.toModel() }, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}