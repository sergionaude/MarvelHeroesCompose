package com.sergionaude.marvelheroes.db

import kotlinx.coroutines.flow.Flow

interface DbRepo {

    suspend fun getCharacterList() : Flow<List<DbCharacter>>

    suspend fun getCharacter(id: Int) : Flow<DbCharacter>

    suspend fun insertCharacter(character: DbCharacter)

    suspend fun updateCharacter(character: DbCharacter)

    suspend fun deleteCharacter(character: DbCharacter)

}