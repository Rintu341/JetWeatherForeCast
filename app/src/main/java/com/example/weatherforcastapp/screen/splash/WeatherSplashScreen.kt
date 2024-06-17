package com.example.weatherforcastapp.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weather.navigation.WeatherScreens
import com.example.weatherforcastapp.R
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(navController: NavController) {

    val scale = remember{
        Animatable(initialValue = 0f)
    }
    val defaultCity = "Kolkata"
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
        navController.navigate(route = WeatherScreens.MainScreen.name + "/$defaultCity"){
            popUpTo(WeatherScreens.SplashScreen.name) { inclusive = true }
        }
    })

    Surface(modifier = Modifier
        .padding(15.dp)
        .size(300.dp)
        .scale(scale.value),
        shape = CircleShape,
        border = BorderStroke(width = 1.dp,color = Color.Gray)
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Image(painter = painterResource(id = R.drawable.img) , contentDescription = "Splash Screen icon ",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit )
            Text("Find the Sun?",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontStyle = FontStyle.Normal,
                color = Color.LightGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherSplashScreenPreView() {
    WeatherSplashScreen(navController = rememberNavController())
}