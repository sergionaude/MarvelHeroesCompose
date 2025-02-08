package com.sergionaude.marvelheroes.data.model

data class CharacterResult(
    val id: Int?,
    val name: String?,
    val description: String?,
    val resourceURI: String?,
    val urls: List<CharacterResultUrl>?,
    val thumbnail: CharacterThumbnail?,
    val comics: CharacterComics?
)
