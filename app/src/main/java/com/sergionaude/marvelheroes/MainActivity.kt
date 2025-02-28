package com.sergionaude.marvelheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.sergionaude.marvelheroes.navigation.CharactersScaffold
import com.sergionaude.marvelheroes.ui.theme.MarvelHeroesTheme
import com.sergionaude.marvelheroes.viewmodel.CollectionViewModel
import com.sergionaude.marvelheroes.viewmodel.LibraryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val lvm by viewModels<LibraryViewModel>()
    val cvm by viewModels<CollectionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarvelHeroesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val navController = rememberNavController()

                    CharactersScaffold(
                        navigationController = navController,
                        libraryViewModel = lvm,
                        collectionViewModel = cvm
                    )
                }
            }
        }
    }
}