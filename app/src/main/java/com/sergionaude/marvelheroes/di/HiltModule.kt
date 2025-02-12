package com.sergionaude.marvelheroes.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sergionaude.marvelheroes.data.api.ApiService
import com.sergionaude.marvelheroes.data.api.MarvelApiRepo
import com.sergionaude.marvelheroes.db.CharacterDao
import com.sergionaude.marvelheroes.db.ComicDb
import com.sergionaude.marvelheroes.db.Constants
import com.sergionaude.marvelheroes.db.DbRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideMarvelRepo() = MarvelApiRepo(apiMarvel = ApiService.api)

    @Provides
    fun provideComicDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(
        context = context,
        klass = ComicDb::class.java,
        name = Constants.DB_DATABASE
    ).build()

    @Provides
    fun provideCharacterDao(comicDb: ComicDb) = comicDb.characterDao()

    @Provides
    fun provideRepoImpl(characterDao: CharacterDao) = DbRepoImpl(characterDao)
}