package com.fernanortega.rickandmorty.data.repository.di

import com.fernanortega.rickandmorty.data.repository.CharacterRepositoryImpl
import com.fernanortega.rickandmorty.data.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): CharacterRepository
}