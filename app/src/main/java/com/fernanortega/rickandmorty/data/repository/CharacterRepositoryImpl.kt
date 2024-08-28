package com.fernanortega.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fernanortega.rickandmorty.data.database.dao.CharacterDao
import com.fernanortega.rickandmorty.data.model.Character
import com.fernanortega.rickandmorty.data.network.services.character.CharacterService
import com.fernanortega.rickandmorty.data.paging.RickAndMortyPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao
) : CharacterRepository {
    override fun getAllCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(20),
            pagingSourceFactory = {
                RickAndMortyPagingSource(
                    characterService = characterService,
                    characterDao = characterDao
                )
            }
        ).flow
    }
}

