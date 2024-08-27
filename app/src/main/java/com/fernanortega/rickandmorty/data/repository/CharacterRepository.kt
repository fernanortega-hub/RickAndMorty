package com.fernanortega.rickandmorty.data.repository

import com.fernanortega.rickandmorty.data.model.Character

interface CharacterRepository {

    suspend fun getAllCharacters(): List<Character>
}