package com.sergionaude.marvelheroes.views.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sergionaude.marvelheroes.data.api.NetworkResult
import com.sergionaude.marvelheroes.data.model.CharacterResult
import com.sergionaude.marvelheroes.data.model.CharactersApiResponse
import com.sergionaude.marvelheroes.navigation.Destination
import com.sergionaude.marvelheroes.utils.CharacterImage
import com.sergionaude.marvelheroes.viewmodel.LibraryViewModel

@Composable
fun LibraryScreen(
    navHostController: NavHostController,
    viewModel: LibraryViewModel,
    paddingValues: PaddingValues)
{
    val result by viewModel.result.collectAsState()
    val text = viewModel.queryText.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = text.value,
            onValueChange = viewModel::newQuery,
//            onValueChange = { viewModel.newQuery(input = text.value) },
            label = { Text(text = "Characters search") },
            placeholder = { Text(text = "Character") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when(result){
                is NetworkResult.Initial -> Text(text = "Search for a character")
                is NetworkResult.Loading -> CircularProgressIndicator()
                is NetworkResult.Success -> ShowCharacterList(result, navHostController)
                is NetworkResult.Error -> Text(text = "Error ${result.message}")
            }
        }

    }
}

@Composable
fun ShowCharacterList(
    result: NetworkResult<CharactersApiResponse>,
    navHostController: NavHostController){

    result.data?.data?.results?.let { characterResults ->
        LazyColumn(
            modifier = Modifier.background(Color.LightGray),
            verticalArrangement = Arrangement.Center
        ) {
            result.data.attributionText?.let { attribution ->
                item {
                    Text(
                        modifier = Modifier.padding(all = 8.dp),
                        text = attribution,
                        fontSize = 12.sp
                    )
                }
            }

            items(characterResults) { charactersData ->
                CharacterCardView(charactersData, navHostController)
            }
        }
    }
}

@Composable
fun CharacterCardView(charactersData: CharacterResult, navHostController: NavHostController){

    val imageUrl = charactersData.thumbnail?.path + "." + charactersData.thumbnail?.extension
    val title = charactersData.name
    val description = charactersData.description
    val context = LocalContext.current
    val id = charactersData.id
    val redirection =
        {
            if (id != null) {
                navHostController.navigate(Destination.CharacterDetail.createRoute(characterId = id))
            } else {
                Toast.makeText(context, "Character id is null", Toast.LENGTH_SHORT).show()
            }
        }
    Column (
        modifier = Modifier
            .padding(all = 4.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                redirection()
            }
    ) {
       Row(
           modifier = Modifier.fillMaxWidth()
       ) {
           CharacterImage(
               url = imageUrl,
               modifier = Modifier
                   .padding(all = 4.dp)
                   .width(width = 100.dp)
           )

           Column(
               modifier = Modifier.padding(all = 4.dp)
           ) {
               Text(
                   text = title?: "", fontWeight = FontWeight.Bold, fontSize = 20.sp
               )
           }
       }
        Text(
            text = description?: "", maxLines = 4, fontSize = 14.sp
        )
    }
}