package com.sergionaude.marvelheroes.di

import com.sergionaude.marvelheroes.data.api.ApiService
import com.sergionaude.marvelheroes.data.api.MarvelApiRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideMarvelRepo() = MarvelApiRepo(apiMarvel = ApiService.api)
}