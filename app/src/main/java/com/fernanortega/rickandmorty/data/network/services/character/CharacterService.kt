package com.fernanortega.rickandmorty.data.network.services.character

import com.fernanortega.rickandmorty.data.network.services.character.response.GetCharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun getCharactersAtPage(@Query("page") page: Int): Response<GetCharactersResponse>

    @GET("character/")
    suspend fun filterCharactersByName(@Query("name") name: String): Response<GetCharactersResponse?>
}