package com.sergionaude.marvelheroes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sergionaude.marvelheroes.data.model.CharacterResult
import com.sergionaude.marvelheroes.utils.comicsToString

@Entity(tableName = Constants.CHARACTER_TABLE)
data class DbCharacter(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val apiId: Int?,
    val name: String?,
    val thumbnail: String?,
    val comics: String?,
    val description: String?
){
    companion object{
        fun fromCharacter(characterResult: CharacterResult) = DbCharacter(
            id = 0,
            apiId = characterResult.id,
            name = characterResult.name,
            thumbnail = characterResult.thumbnail?.path + "." + characterResult.thumbnail?.extension,
            comics = characterResult.comics?.items?.mapNotNull {
                it.name
            }?.comicsToString() ?: "no comics",
            description = characterResult.description
        )
    }
}
