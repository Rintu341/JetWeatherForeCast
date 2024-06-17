package com.example.weatherforcastapp.screen.main

import androidx.lifecycle.ViewModel
import com.example.weatherforcastapp.data.DataOrException
import com.example.weatherforcastapp.model.apiModel.WeatherObject
import com.example.weatherforcastapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) :ViewModel(){

    suspend fun getWeatherData(city: String, units: String) : DataOrException<WeatherObject,Boolean,Exception>
    {
        return weatherRepository.getWeather(city,units)
    }
    /*
    private var _data = MutableLiveData<DataOrException<WeatherObject,Boolean,Exception>>()
    val data:LiveData<DataOrException<WeatherObject,Boolean,Exception>> = _data
    init {
        loadWeather()
    }

    private  fun loadWeather()
    {
        getWeather("London")
    }

    fun getWeather(city:String)
    {
        viewModelScope.launch {
            if(city.isEmpty()) return@launch
            _data.value = weatherRepository.getWeather(city)
            if(_data.value?.data.toString().isNotEmpty())   _data.value?.loading = false

        }
        Log.d("getWeather", _data.value?.data.toString())
    }
     */
}