package com.example.weatherforcastapp.di

import com.example.weatherforcastapp.network.WeatherApi
import com.example.weatherforcastapp.repository.WeatherRepository
import com.example.weatherforcastapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi():WeatherApi {
        return  Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApi: WeatherApi):WeatherRepository = WeatherRepository(weatherApi)




}