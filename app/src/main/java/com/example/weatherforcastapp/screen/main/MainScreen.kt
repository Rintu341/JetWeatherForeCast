package com.example.weatherforcastapp.screen.main


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weather.navigation.WeatherScreens
import com.example.weatherforcastapp.data.DataOrException
import com.example.weatherforcastapp.model.apiModel.WeatherObject
import com.example.weatherforcastapp.screen.setting.SettingViewModel
import com.example.weatherforcastapp.widgets.MainContent
import com.example.weatherforcastapp.widgets.WeatherAppBar

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel(),
    city: String?
) {

    /*

    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    val locationViewModel = viewModel<LocationViewModel>()
    val location = locationViewModel.location.value
    val address = location?.let { locationUtils.getAddressFromLatLng(it) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // after getting the result check user grant the permissions or not
        if(permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            &&
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            // I have access to location
            locationUtils.requestLocationUpdates(viewModel = locationViewModel)
        }else{
            // ask for permission
            // I should rationalize why app need permission
            val rationalRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                context as MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                context ,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            if(rationalRequired)
            {
                Toast.makeText(context,
                    "Location permission is require for getting current location weather",
                    Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,
                    "Location permission is required go to setting go to setting to grant location permission",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
//    if (locationUtils.hasLocationPermission()) {
//        //I have access the location
//        locationUtils.requestLocationUpdates(locationViewModel)
//    } else {
//        // ask for permission
//        requestPermissionLauncher.launch(
//            arrayOf(
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//        )
//    }

     */

    val unitFromDb = settingViewModel.unitList.collectAsState().value
    var unit by remember{
        mutableStateOf("imperial")
    }
    var isImperial by remember{
        mutableStateOf(false)
    }

    if(!unitFromDb.isNullOrEmpty())
    {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"

        val weatherData = produceState<DataOrException<WeatherObject,Boolean,Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(city.toString(),units = unit)
//        value =  mainViewModel.getWeatherData(address.toString())
        }

        when{
            weatherData.value.loading == true ->
            {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    CircularProgressIndicator()
                }
            }
            weatherData.value.e != null ->{
                Text("Something is wrong ")
            }
            else->{
                MainScaffold(weatherData = weatherData,navController,unit)
            }
        }
    }else{
        unit = "metric"

        val weatherData = produceState<DataOrException<WeatherObject,Boolean,Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(city.toString(),units = unit)
//        value =  mainViewModel.getWeatherData(address.toString())
        }

        when{
            weatherData.value.loading == true ->
            {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    CircularProgressIndicator()
                }
            }
            weatherData.value.e != null ->{
                Text("Something is wrong ")
            }
            else->{
                MainScaffold(weatherData = weatherData,navController,unit)
            }
        }
    }

}
@Composable
fun MainScaffold(
    weatherData: State<DataOrException<WeatherObject, Boolean, Exception>>,
    navController: NavController,
    unit: String
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
            MainContent(weatherData,unit)

        }
    }
}
@Preview
@Composable
private fun MainScreenPreView() {

}