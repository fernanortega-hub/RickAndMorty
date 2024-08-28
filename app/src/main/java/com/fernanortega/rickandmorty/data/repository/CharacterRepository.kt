package com.fernanortega.rickandmorty.data.repository

import com.fernanortega.rickandmorty.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<List<Character>>
    suspend fun refreshCharactersFromNetwork(): Result<Unit>
}