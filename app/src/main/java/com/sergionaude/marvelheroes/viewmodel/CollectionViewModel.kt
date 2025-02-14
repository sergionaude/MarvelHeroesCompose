package com.sergionaude.marvelheroes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergionaude.marvelheroes.data.model.CharacterResult
import com.sergionaude.marvelheroes.db.DbCharacter
import com.sergionaude.marvelheroes.db.DbRepo
import com.sergionaude.marvelheroes.db.DbRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val repoDb: DbRepoImpl) : ViewModel() {

    val currentCharacter = MutableStateFlow<DbCharacter?>(null)
    val collection = MutableStateFlow<List<DbCharacter>>(listOf())

    init {
       getCollection()
    }

    private fun getCollection(){
        viewModelScope.launch(Dispatchers.IO) {
            repoDb.getCharacterList().collect{
                collection.value = it
            }
        }
    }

    fun setCurrentCharacterId(characterId : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repoDb.getCharacter(id = characterId).collect{
                currentCharacter.value = it
            }
        }
    }

   fun addCharacter(characterResult: CharacterResult){
        viewModelScope.launch(Dispatchers.IO) {
            repoDb.insertCharacter(character = DbCharacter.fromCharacter(characterResult = characterResult))
        }
    }

    fun removeCharacter(characterResult: CharacterResult){
        viewModelScope.launch {
            repoDb.deleteCharacter(character = DbCharacter.fromCharacter(characterResult))
        }
    }
}