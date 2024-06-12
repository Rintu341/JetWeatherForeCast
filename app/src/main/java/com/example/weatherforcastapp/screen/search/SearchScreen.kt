package com.example.weatherforcastapp.screen.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weather.navigation.WeatherScreens
import com.example.weatherforcastapp.widgets.WeatherAppBar

@Composable
fun SearchScreen(
                 navController: NavController)
{
    Scaffold(
        topBar = {
            WeatherAppBar(
                navController = navController,
                title = "Search",
                isMainScreen = false,
                icon = Icons.Filled.ArrowBack
            ){

            }
        }
    )
    { it ->
        Column(
            modifier = Modifier.padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            SearchBar(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
            ){place ->
                Log.d("place","Search Place $place")
                navController.navigate(WeatherScreens.MainScreen.name +"/$place"){
                        popUpTo(WeatherScreens.SearchScreen.name) { inclusive = true }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBar(modifier: Modifier = Modifier,
              onSearch:(String) ->Unit = {}) {
    val searchQueryState =
        rememberSaveable {
            mutableStateOf("")
        }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value){
            searchQueryState.value.trim().isNotBlank()
    }
    ColumnTextField(
        valueState = searchQueryState,
        placeholder = "Kolkata",
        onAction = KeyboardActions{
            if(!valid) return@KeyboardActions
            onSearch(searchQueryState.value.trim())
            searchQueryState.value = ""
            keyboardController?.hide()
        }
    )
    
}

@Composable
fun ColumnTextField(valueState: MutableState<String>,
                    placeholder: String,
                    keyboardType: KeyboardType = KeyboardType.Text,
                    imeAction : ImeAction = ImeAction.Search,
                    onAction : KeyboardActions = KeyboardActions.Default)
{
    OutlinedTextField(value = valueState.value,
        onValueChange = {
        valueState.value = it
        },
        placeholder = {Text(placeholder)},
        label = {Text("enter location name")},
        maxLines = 1,
        singleLine = true,
        keyboardOptions =  KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        )
}
