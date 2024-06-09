package com.example.weatherforcastapp.repository

import android.util.Log
import com.example.weatherforcastapp.data.DataOrException
import com.example.weatherforcastapp.model.WeatherObject
import com.example.weatherforcastapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository  @Inject constructor(
    private val weatherApi: WeatherApi
){
    suspend fun getWeather(city :String) : DataOrException<WeatherObject,Boolean,Exception>
    {
       val response = try {
            weatherApi.getWeather(location = city)
        }catch (e:Exception)
       {
           Log.d("wrong", e.toString())
            return DataOrException(e = e)
        }
        return DataOrException(data = response, loading = false)
    }
}