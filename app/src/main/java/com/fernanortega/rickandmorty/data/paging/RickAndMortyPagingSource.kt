package com.fernanortega.rickandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fernanortega.rickandmorty.data.database.dao.CharacterDao
import com.fernanortega.rickandmorty.domain.model.Character

class RickAndMortyPagingSource(
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

            val results = localResults.map { it.toModel() }
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (results.isEmpty()) null else page + 1

            return LoadResult.Page(data = results, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}