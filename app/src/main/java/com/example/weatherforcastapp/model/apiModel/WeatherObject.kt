package com.example.weatherforcastapp.model.apiModel

data class WeatherObject(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherItem>,
    val message: Double
)