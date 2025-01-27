package com.sergionaude.marvelheroes.views.commons

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sergionaude.marvelheroes.R
import com.sergionaude.marvelheroes.navigation.Destination


@Composable
fun CharactersBottomBar(navHostController: NavHostController){
    BottomNavigation(
        elevation = 8.dp
    ) {
        val navBackStackEntry = navHostController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        val iconLibrary = painterResource(R.drawable.ic_library)
        val iconCollection = painterResource(R.drawable.ic_collection)

        BottomNavigationItem(
            selected = currentDestination?.route == Destination.Library.route,
            onClick = {
                navHostController.navigate(Destination.Library.route){
                    popUpTo(Destination.Library.route)
                    launchSingleTop
                }
            },
            icon = { Icon(painter = iconLibrary, contentDescription = null) },
            label = { Text(text = Destination.Library.route) }
        )
        BottomNavigationItem(
            selected = currentDestination?.route == Destination.Collection.route,
            onClick = {
                navHostController.navigate(Destination.Collection.route){
                    popUpTo(Destination.Collection.route)
                    launchSingleTop
                }
            },
            icon = { Icon(painter = iconCollection, contentDescription = null) },
            label = { Text(Destination.Collection.route) }
        )
    }
}
