package com.sergionaude.marvelheroes.data.api

import com.sergionaude.marvelheroes.data.model.CharactersApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarvelApiRepo(private val apiMarvel: MarvelAPI) {

    val characters = MutableStateFlow<NetworkResult<CharactersApiResponse>>(NetworkResult.Initial())

    suspend fun query(query: String) {
        characters.value = NetworkResult.Loading()

        apiMarvel.getCharacters(name = query)
            .enqueue(object : Callback<CharactersApiResponse> {
                override fun onResponse(
                    data: Call<CharactersApiResponse>,
                    response: Response<CharactersApiResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { info ->
                            characters.value = NetworkResult.Success(data = info)
                        }
                    } else {
                        response.message().let {
                            characters.value = NetworkResult.Error(message = it)
                        }
                    }
                }

                override fun onFailure(call: Call<CharactersApiResponse>, error: Throwable) {
                    error.localizedMessage.let {
                        characters.value = NetworkResult.Error(message = it)
                    }
                    error.printStackTrace()
                }
            })
    }
}