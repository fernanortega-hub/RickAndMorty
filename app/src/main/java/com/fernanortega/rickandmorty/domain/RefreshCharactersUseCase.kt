package com.fernanortega.rickandmorty.domain

import com.fernanortega.rickandmorty.data.repository.CharacterRepository
import javax.inject.Inject

class RefreshCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke() = characterRepository.refreshCharactersFromNetwork()
}