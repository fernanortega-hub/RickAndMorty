package com.fernanortega.rickandmorty.domain

import androidx.paging.PagingData
import com.fernanortega.rickandmorty.domain.model.Character
import com.fernanortega.rickandmorty.data.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(): Flow<PagingData<Character>> = characterRepository.getAllCharacters()
}