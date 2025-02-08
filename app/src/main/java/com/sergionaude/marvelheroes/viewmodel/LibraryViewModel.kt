package com.sergionaude.marvelheroes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergionaude.marvelheroes.data.api.MarvelApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel
@Inject constructor(private val repo: MarvelApiRepo) : ViewModel() {

    val result = repo.characters
    val queryText = MutableStateFlow("")
    private val queryInput = Channel<String>(Channel.CONFLATED)


    init {
        retrieveCharacters()
    }

    private fun validateQuery(query: String) = query.length > 2

    @OptIn(FlowPreview::class)
    private fun retrieveCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            queryInput.receiveAsFlow()
                .filter { validateQuery(query = it) }
                .debounce(1000)
                .collect{
                    repo.query(query = it)
                }
        }
    }

    fun newQuery(input: String){
        queryText.value = input
        queryInput.trySend(element = input)
    }
}