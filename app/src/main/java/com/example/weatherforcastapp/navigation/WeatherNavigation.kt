package com.example.weatherforcastapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weather.navigation.WeatherScreens
import com.example.weatherforcastapp.screen.main.MainScreen
import com.example.weather.screen.splash.WeatherSplashScreen
import com.example.weatherforcastapp.screen.main.MainViewModel
import com.example.weatherforcastapp.screen.search.SearchScreen

@Composable
fun WeatherNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val mainViewModel = hiltViewModel<MainViewModel>()


    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(route = WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }
        val route = WeatherScreens.MainScreen.name
        composable(route = "$route/{city}",
            arguments = listOf(
                navArgument(name = "city"){
                    type = NavType.StringType
                }
            )
        ){navBackStackEntry ->
            navBackStackEntry.arguments?.getString("city").let{city->
                MainScreen(navController = navController,mainViewModel,city)
            }
        }
        composable(route = WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }

    }

}