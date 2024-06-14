package com.example.weatherforcastapp.screen.main


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.weather.navigation.WeatherScreens
import com.example.weatherforcastapp.data.DataOrException
import com.example.weatherforcastapp.model.apiModel.WeatherObject
import com.example.weatherforcastapp.widgets.MainContent
import com.example.weatherforcastapp.widgets.WeatherAppBar

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    city: String?
) {
        val weatherData = produceState<DataOrException<WeatherObject,Boolean,Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(city.toString())
        }

        when{
            weatherData.value.loading == true ->
            {
                CircularProgressIndicator()
            }
            weatherData.value.e != null ->{
                Text("Something is wrong ")
            }
            else->{
            MainScaffold(weatherData = weatherData,navController)
            }
        }
}
@Composable
fun MainScaffold(
    weatherData: State<DataOrException<WeatherObject, Boolean, Exception>>,
    navController: NavController
) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                navController,
                onSearchClick = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                },
                onMenuClick = {

                },
                isMainScreen = true,
                title = (weatherData.value.data!!.city.name + "," + weatherData.value.data!!.city.country),
                icon = null
//                    Icons.Filled.ArrowBack
            )
        }
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            MainContent(weatherData)

        }
    }
}
@Preview
@Composable
private fun MainScreenPreView() {

}