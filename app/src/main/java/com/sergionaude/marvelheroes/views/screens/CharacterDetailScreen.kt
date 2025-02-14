package com.sergionaude.marvelheroes.views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sergionaude.marvelheroes.navigation.Destination
import com.sergionaude.marvelheroes.utils.CharacterImage
import com.sergionaude.marvelheroes.utils.comicsToString
import com.sergionaude.marvelheroes.viewmodel.CollectionViewModel
import com.sergionaude.marvelheroes.viewmodel.LibraryViewModel

@Composable
fun CharacterDetailScreen(
    lvm: LibraryViewModel,
    cvm: CollectionViewModel,
    paddingValues: PaddingValues,
    navController: NavController
){
    val character = lvm.characterDetails.value
    val collection by cvm.collection.collectAsState()
    val inCollection = collection.map { it.apiId }.contains(character?.id)

    if (character == null){
        navController.navigate(Destination.CharacterDetail.route){
            popUpTo(Destination.Library.route)
            launchSingleTop = true
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (character?.id != null){
            cvm.setCurrentCharacterId(characterId = character.id)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 4.dp)
            .padding(bottom = paddingValues.calculateBottomPadding())
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = character?.thumbnail?.path + "." + character?.thumbnail?.extension
        val title = character?.name ?: "No name"
        val comics = character?.comics?.items?.mapNotNull { it.name }?.comicsToString() ?: "No comics"
        val description = character?.description ?: "No description"

        CharacterImage(
            url = imageUrl,
            modifier = Modifier
                .width(200.dp)
                .padding(4.dp),
        )

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(all = 4.dp)
        )

        Text(
            text = comics,
            fontStyle = FontStyle.Italic,
            fontSize = 14.sp,
            modifier = Modifier.padding(all = 4.dp)
        )

        Text(
            text = description,
            fontSize = 12.sp,
            modifier = Modifier.padding(4.dp)
        )

        Button(
            onClick = {if (!inCollection && character?.id != null){
                cvm.addCharacter(characterResult = character)
            } },
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            if (!inCollection){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                    Text(
                        text = "Add to collection"
                    )
                }
            }
            else{
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                    Text(
                        text = "Added"
                    )
                }
            }
            }
        }
    }