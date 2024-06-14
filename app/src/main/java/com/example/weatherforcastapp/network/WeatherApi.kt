package com.example.weatherforcastapp.network

import com.example.weatherforcastapp.model.apiModel.WeatherObject
import com.example.weatherforcastapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily?")
    suspend fun getWeather(
        @Query(value = "q")location:String ,
        @Query(value = "appid")API_KEY:String = Constants.API_KEY,
        @Query(value = "units") units:String  = "imperial"
    ) : WeatherObject

}