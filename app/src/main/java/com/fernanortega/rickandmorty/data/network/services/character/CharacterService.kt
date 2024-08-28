package com.fernanortega.rickandmorty.data.network.services.character

import com.fernanortega.rickandmorty.data.network.services.character.response.AllCharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface CharacterService {

    @GET("character")
    suspend fun getAllCharacters(): Response<AllCharacterResponse>
}