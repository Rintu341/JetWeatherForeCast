package com.example.weatherforcastapp.model.apiModel

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)