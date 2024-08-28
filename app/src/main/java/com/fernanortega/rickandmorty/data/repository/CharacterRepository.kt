package com.fernanortega.rickandmorty.data.repository

import androidx.paging.PagingData
import com.fernanortega.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<PagingData<Character>>
}