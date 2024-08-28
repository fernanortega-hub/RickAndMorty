package com.fernanortega.rickandmorty.data.database.di

import com.fernanortega.rickandmorty.data.database.schemas.RealmCharacter
import com.fernanortega.rickandmorty.data.database.schemas.RealmLocationCharacter
import com.fernanortega.rickandmorty.data.database.schemas.RealmOriginCharacter
import com.fernanortega.rickandmorty.data.database.schemas.RealmRecentSearch
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {
    @Singleton
    @Provides
    fun providesRealmConfiguration(): RealmConfiguration = RealmConfiguration.Builder(
        schema = setOf(
            RealmCharacter::class,
            RealmLocationCharacter::class,
            RealmOriginCharacter::class,
            RealmRecentSearch::class
        )
    )
        .name("rickAndMortyDb.realm")
        .build()

    @Singleton
    @Provides
    fun providesRickAndMortyDatabase(
        config: RealmConfiguration
    ): Realm {
        return Realm.open(config)
    }
}
