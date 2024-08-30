package com.fernanortega.rickandmorty.data.repository

import androidx.paging.PagingData
import com.fernanortega.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    /**
     * Obtiene todos los personajes paginados, listos para ser consumidos por la UI.
     */
    fun getAllCharacters(): Flow<PagingData<Character>>

    /**
     * Obtiene un personaje por su id, ya sea de la base de datos local (si existe) o de la api
     */
    suspend fun getCharacterById(id: Int): Result<Character?>
}