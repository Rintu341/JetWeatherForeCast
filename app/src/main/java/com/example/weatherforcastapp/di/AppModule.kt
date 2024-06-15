package com.example.weatherforcastapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherforcastapp.data.WeatherDao
import com.example.weatherforcastapp.data.WeatherDatabase
import com.example.weatherforcastapp.network.WeatherApi
import com.example.weatherforcastapp.repository.WeatherRepository
import com.example.weatherforcastapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase):WeatherDao
    = weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideWeatherDatabase(@ApplicationContext context: Context):WeatherDatabase
    = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        "weatherDatabase"
    ).fallbackToDestructiveMigration()
        .build()

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