package com.fernanortega.rickandmorty.data.repository

import com.fernanortega.rickandmorty.common.AppDispatchers
import com.fernanortega.rickandmorty.common.Dispatcher
import com.fernanortega.rickandmorty.data.database.dao.RecentSearchDao
import com.fernanortega.rickandmorty.data.database.schemas.RealmRecentSearch
import com.fernanortega.rickandmorty.domain.model.RecentSearch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentSearchRepositoryImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : RecentSearchRepository {
    override fun getAllRecentSearches(): Flow<List<RecentSearch>> =
        recentSearchDao.getAllRecentSearches().map { results ->
            results.list.map { it.toModel() }
        }

    override suspend fun insertRecentSearch(query: String) {
        withContext(ioDispatcher) {
            recentSearchDao.insertRecentSearch(
                RealmRecentSearch().apply {
                    this.query = query
                }
            )
        }
    }

    override suspend fun deleteAllRecentSearches() {
        withContext(ioDispatcher) {
            recentSearchDao.deleteAllRecentSearches()
        }
    }

}