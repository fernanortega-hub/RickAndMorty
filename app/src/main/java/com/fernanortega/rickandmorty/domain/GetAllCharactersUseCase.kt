package com.fernanortega.rickandmorty.domain

import com.fernanortega.rickandmorty.data.model.Character
import com.fernanortega.rickandmorty.data.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(): Flow<List<Character>> = characterRepository.getAllCharacters()
}