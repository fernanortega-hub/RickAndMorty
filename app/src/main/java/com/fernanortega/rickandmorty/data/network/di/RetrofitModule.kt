package com.fernanortega.rickandmorty.data.network.di

import com.fernanortega.rickandmorty.BuildConfig
import com.fernanortega.rickandmorty.data.network.services.character.CharacterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        json: Json
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BACKEND_URL)
        .addConverterFactory(
            json.asConverterFactory("application/json; charset=UTF8".toMediaType())
        )
        .build()


    @Provides
    @Singleton
    fun providesCharacterService(
        retrofit: Retrofit
    ): CharacterService = retrofit.create(CharacterService::class.java)
}