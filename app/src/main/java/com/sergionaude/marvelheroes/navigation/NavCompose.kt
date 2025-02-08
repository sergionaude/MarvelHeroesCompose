package com.sergionaude.marvelheroes.navigation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sergionaude.marvelheroes.viewmodel.LibraryViewModel
import com.sergionaude.marvelheroes.views.commons.CharactersBottomBar
import com.sergionaude.marvelheroes.views.screens.CollectionScreen
import com.sergionaude.marvelheroes.views.screens.LibraryScreen

@Composable
fun NavCompose(
    navigationController: NavHostController,
    libraryViewModel: LibraryViewModel
){
    val navController = rememberNavController()
    CharactersScaffold(navigationController = navController, libraryViewModel = libraryViewModel)
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CharactersScaffold(navigationController: NavHostController, libraryViewModel: LibraryViewModel){
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { CharactersBottomBar(navHostController = navigationController) }
    ) { paddingValues ->
        NavHost(navController = navigationController, startDestination = Destination.Library.route){
            composable(route = Destination.Library.route){
                LibraryScreen(
                    viewModel =  libraryViewModel,
                    navHostController = navigationController,
                    paddingValues = paddingValues
                )
            }
            composable(route = Destination.Collection.route) {
                CollectionScreen()
            }
            composable(route = Destination.CharacterDetail.route) {

            }
        }
    }
}