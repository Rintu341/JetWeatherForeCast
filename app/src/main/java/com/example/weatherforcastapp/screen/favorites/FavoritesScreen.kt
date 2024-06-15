package com.example.weatherforcastapp.screen.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weather.navigation.WeatherScreens
import com.example.weatherforcastapp.model.favoritesModel.Favorite
import com.example.weatherforcastapp.widgets.WeatherAppBar

//This should i do not do
/*
@Composable
fun FavoritesScreen(
                    navController: NavController,
                    favoriteViewModel: FavoriteViewModel = hiltViewModel()) {
        Scaffold(
            topBar = {
                WeatherAppBar(
                    navController = navController,
                    isMainScreen = false,
                    title = "My Cites",
                    icon = Icons.Default.ArrowBack
                    )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                val favoriteList = favoriteViewModel.favoriteList.collectAsState().value
                Log.d("list1","$favoriteList")
                when{
                        favoriteList.loading == true -> {
                            Column (
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                CircularProgressIndicator()
                            }
                        }

                        favoriteList.e != null ->{
                            Column (
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(" error")
                            }
                        }

                        favoriteList.data != null ->{
                            LazyColumn {

                                items(favoriteList.data!!)
                                {favorite->
                                    CityInfo(navController = navController, favorite,favoriteViewModel)

                                }
                            }
                        }

                        else-> {
                            Column (
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text("empty list")
                            }
                        }
                    }
//
            }
        }
}

 */

@Composable
fun FavoritesScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoriteListState by favoriteViewModel.favoriteList.collectAsState()

    Scaffold(
        topBar = {
            WeatherAppBar(
                navController = navController,
                isMainScreen = false,
                title = "My Cities",
                icon = Icons.Default.ArrowBack
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                favoriteListState.loading == true -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
                favoriteListState.e != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Error: ${favoriteListState.e?.localizedMessage}")
                    }
                }
                favoriteListState.data != null -> {
                    LazyColumn {
                        items(favoriteListState.data!!) { favorite ->
                            CityInfo(navController = navController, favorite = favorite, favoriteViewModel = favoriteViewModel)
                        }
                    }
                }
                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Empty list")
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun CityInfo(navController: NavController,
             favorite: Favorite = Favorite("Kolkata","IN"),
             favoriteViewModel: FavoriteViewModel) {

    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        shadowElevation = 4.dp
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Row (
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .clickable {
                            navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(favorite.city+","+favorite.country,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic)

//                    Text("43°c",
//                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
//                        fontWeight = FontWeight.ExtraBold,
//                        )
                }
                IconButton(onClick = {
                        favoriteViewModel.deleteFavorite(favorite)
                }) {
                    Icon( imageVector = Icons.Default.Delete, contentDescription = null)
                }

            }
    }

}