package com.sergionaude.marvelheroes.navigation

sealed class Destination(val route: String) {
    data object Library : Destination(route = "library")
    data object Collection: Destination(route = "collection")
    data object CharacterDetail: Destination(route = "character/{characterId}"){
        fun createRoute(characterId : Int) = "character/$characterId"
    }
}