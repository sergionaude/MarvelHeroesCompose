package com.sergionaude.marvelheroes.navigation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sergionaude.marvelheroes.viewmodel.CollectionViewModel
import com.sergionaude.marvelheroes.viewmodel.LibraryViewModel
import com.sergionaude.marvelheroes.views.commons.CharactersBottomBar
import com.sergionaude.marvelheroes.views.screens.CharacterDetailScreen
import com.sergionaude.marvelheroes.views.screens.CollectionScreen
import com.sergionaude.marvelheroes.views.screens.LibraryScreen

@Composable
fun CharactersScaffold(
    navigationController: NavHostController,
    libraryViewModel: LibraryViewModel,
    collectionViewModel: CollectionViewModel){
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

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
            composable(route = Destination.CharacterDetail.route) { navBackStackEntry ->

                val id = navBackStackEntry.arguments?.getString("characterId")?.toIntOrNull()

                if (id == null){
                    Toast.makeText(
                        context,
                        "Character id is requiered",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else{
                    libraryViewModel.retrieveSingleCharacter(id = id)
                    CharacterDetailScreen(
                        lvm = libraryViewModel,
                        cvm = collectionViewModel,
                        paddingValues = paddingValues,
                        navController = navigationController
                    )
                }
            }
        }
    }
}