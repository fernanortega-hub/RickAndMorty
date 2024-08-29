package com.fernanortega.rickandmorty.domain

import com.fernanortega.rickandmorty.data.repository.CharacterRepository
import com.fernanortega.rickandmorty.domain.model.Character
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCharacterByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): Result<Character?> = characterRepository.getCharacterById(id)
}