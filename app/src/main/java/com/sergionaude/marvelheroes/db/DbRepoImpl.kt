package com.sergionaude.marvelheroes.db

import kotlinx.coroutines.flow.Flow

class DbRepoImpl(private val characterDao: CharacterDao) : DbRepo {
    override suspend fun getCharacterList(): Flow<List<DbCharacter>> =
        characterDao.getCharacterList()

    override suspend fun getCharacter(id: Int): Flow<DbCharacter> =
        characterDao.getCharacter(characterId = id)

    override suspend fun insertCharacter(character: DbCharacter) =
        characterDao.insertCharacter(character = character)

    override suspend fun updateCharacter(character: DbCharacter) =
        characterDao.updateCharacter(character = character)

    override suspend fun deleteCharacter(character: DbCharacter) =
        characterDao.deleteCharacter(character = character)
}