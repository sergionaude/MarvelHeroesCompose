package com.sergionaude.marvelheroes.data.api

import com.sergionaude.marvelheroes.data.model.CharactersApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelAPI {

    @GET("characters")
    suspend fun getCharacters(@Query("nameStartsWith") name: String): Call<CharactersApiResponse>
}