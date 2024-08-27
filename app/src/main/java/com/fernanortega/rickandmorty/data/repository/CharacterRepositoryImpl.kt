package com.fernanortega.rickandmorty.data.repository

import com.fernanortega.rickandmorty.data.network.services.character.CharacterService
import com.fernanortega.rickandmorty.data.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService
): CharacterRepository {
    override suspend fun getAllCharacters(): List<Character> {
        return withContext(Dispatchers.IO) {
            return@withContext characterService.getAllCharacters()?.results?.map { it.toModel() } ?: emptyList()
        }
    }
}