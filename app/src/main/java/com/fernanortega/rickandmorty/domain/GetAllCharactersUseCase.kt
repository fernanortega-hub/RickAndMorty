package com.fernanortega.rickandmorty.domain

import com.fernanortega.rickandmorty.data.model.Character
import com.fernanortega.rickandmorty.data.repository.CharacterRepository
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(): List<Character> = characterRepository.getAllCharacters()
}