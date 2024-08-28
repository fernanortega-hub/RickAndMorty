package com.fernanortega.rickandmorty.data.repository.di

import com.fernanortega.rickandmorty.data.repository.CharacterRepositoryImpl
import com.fernanortega.rickandmorty.data.repository.CharacterRepository
import com.fernanortega.rickandmorty.data.repository.RecentSearchRepository
import com.fernanortega.rickandmorty.data.repository.RecentSearchRepositoryImpl
import com.fernanortega.rickandmorty.data.repository.SearchContentRepository
import com.fernanortega.rickandmorty.data.repository.SearchContentRepositoryImpl
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

    @Binds
    abstract fun bindsRecentSearchRepository(
        recentSearchRepositoryImpl: RecentSearchRepositoryImpl
    ): RecentSearchRepository

    @Binds
    abstract fun bindsSearchContentRepository(
        searchContentRepositoryImpl: SearchContentRepositoryImpl
    ): SearchContentRepository
}