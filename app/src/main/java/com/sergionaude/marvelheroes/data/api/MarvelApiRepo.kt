package com.sergionaude.marvelheroes.data.api

import androidx.compose.runtime.mutableStateOf
import com.sergionaude.marvelheroes.data.model.CharacterResult
import com.sergionaude.marvelheroes.data.model.CharactersApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarvelApiRepo(private val apiMarvel: MarvelAPI) {

    val characters = MutableStateFlow<NetworkResult<CharactersApiResponse>>(NetworkResult.Initial())
    val characterDetails = mutableStateOf<CharacterResult?>(value = null)


     fun query(query: String) {
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

    fun getSingleCharacter(characterId : Int){
        characterId.let {
            characterDetails.value = characters.value?.data?.data?.results?.firstOrNull { character ->
                character.id == characterId
            }
        }
    }
}