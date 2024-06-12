package com.example.weatherforcastapp.widgets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.weather.navigation.WeatherScreens
import com.example.weatherforcastapp.R
import com.example.weatherforcastapp.data.DataOrException
import com.example.weatherforcastapp.model.WeatherObject
import com.example.weatherforcastapp.utils.fahrenheitToCelsius
import com.example.weatherforcastapp.utils.formatDate
import com.example.weatherforcastapp.utils.formatDateTime
import com.example.weatherforcastapp.utils.formatDateTimeInNumber
import com.example.weatherforcastapp.utils.formatJustDate



@Composable
@OptIn(ExperimentalMaterial3Api::class)
 fun WeatherAppBar(
//    weatherData: State<DataOrException<WeatherObject, Boolean, Exception>>,
    navController: NavController,
    onMenuClick:() -> Unit = {},
    isMainScreen:Boolean = true,
    title:String = "Title",
    icon : ImageVector? = null,
    onSearchClick:() ->Unit = {},
) {
    // this scrollBehavior is used for to add elevation in top app bar at a time user scroll
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    CenterAlignedTopAppBar(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Row {
                if(isMainScreen) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = null
                        )
                    }
                }
                Text(title)
            }
        },
        actions = {
            if(isMainScreen) {
                IconButton(onClick = {
                    onSearchClick.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Localized description"
                    )
                }

                IconButton(onClick = {
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        navigationIcon = {
            if(icon != null) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun MainContent(weatherData: State<DataOrException<WeatherObject, Boolean, Exception>>) {
    val imageUrl = remember {
        mutableStateOf("https://openweathermap.org/img/wn/${weatherData.value.data!!.list[0].weather[0].icon}.png")
    }
    val weatherItem = weatherData.value.data!!.list[0]
    val index = remember {
        mutableIntStateOf(0)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        TopMainContent(weatherItem.temp.day,
            imageUrl.value,
            formatDate(weatherItem.dt),
            weatherItem.weather[0].main
        )
        MiddleMainContent(humidity = weatherItem.humidity.toString(),
            feelsLike = fahrenheitToCelsius(weatherItem.feels_like.day),
            speed = weatherItem.speed.toString())
        Log.d("size","${weatherData.value.data!!.list.size}")
        Divider()
        SunShineAndSunRise(sunset = weatherItem.sunset,sunrise = weatherItem.sunrise)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        )
        {
            Text("This Week",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.ExtraBold)
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color.Transparent,
        ) {
            LazyColumn {
                items(weatherData.value.data!!.list.size)
                {index->
                    WeatherDetailsRow(weatherData = weatherData, index = index )
                }
            }

        }
    }
}
/*
                items(weatherData.value.data!!.list)
                {item ->
                    WeatherDetailsRow(weatherData = weatherData, index = index.intValue )
                }
 */

@Composable
fun WeatherDetailsRow(weatherData: State<DataOrException<WeatherObject, Boolean, Exception>>, index:Int) {

//    val imageUrl = remember {
//        mutableStateOf("https://openweathermap.org/img/wn/${weatherData.value.data!!.list[0].weather[0].icon}.png")
//    }
    val weatherItem = weatherData.value.data
    val milliseconds = weatherItem!!.list[index].sunrise
    val space : Dp = 20.dp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Row {
                Text(text = formatDateTimeInNumber(milliseconds))
                Spacer(modifier = Modifier.width(space))
                Text(text = formatJustDate(milliseconds))
            }
            Box() {
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/${weatherData.value.data!!.list[index].weather[0].icon}.png",
                    contentDescription = "Weather icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            }
            Row {
                Text( text = fahrenheitToCelsius(weatherItem.list[index].temp.max))
                Spacer(modifier = Modifier.width(space))
                Text( text = fahrenheitToCelsius(weatherItem.list[index].temp.min))
            }
        }
    }
}

@Composable
fun SunShineAndSunRise(sunset: Int, sunrise: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id =  R.drawable.sunrise),
                contentDescription = "sunrise",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )
            Text(text = formatDateTime(sunrise),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize)
        }

        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = formatDateTime(sunset),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize)
            Image(
                painter = painterResource(id =  R.drawable.sunset),
                contentDescription = "sunset",
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 8.dp)
            )

        }

    }
}


@Preview(showBackground = true)
@Composable
fun TopMainContent(temp: Double = 84.87,
                   iconUrl: String = "icon",
                   date:String = "Thu, Oct 34",
                   currentWeather:String = "Clear"
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(percent = 10),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .background(Color.Transparent)
                    .padding(all = 4.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.padding(start = 8.dp),
                    contentAlignment = Alignment.Center
                )
                {
                    Column {
                        Text(
                            text = fahrenheitToCelsius(temp) +"°c",
                            fontSize = 70.sp
                        )
                        Text(
                            currentWeather,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
                Box(
                    contentAlignment = Alignment.Center
                )
                {
                    AsyncImage(
                        model = iconUrl,
                        contentDescription = "Weather icon",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    date,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun MiddleMainContent(humidity:String = "10",feelsLike: String = "45",speed:String = "20") {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "$humidity%",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.feelslike),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "$feelsLike°c",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.windy),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = speed,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
 fun MiddleEachContent(content: String, icon:Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.33f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = content)
            Icon(
                painter = painterResource( icon),
                contentDescription = null,
            )
            Text(stringResource(id = R.string.humidity))
        }
    }
}