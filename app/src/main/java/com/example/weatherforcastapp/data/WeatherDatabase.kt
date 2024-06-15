package com.example.weatherforcastapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherforcastapp.model.favoritesModel.Favorite

@Database(version = 1, entities =[Favorite::class], exportSchema = false )
abstract class WeatherDatabase() : RoomDatabase(){
    abstract fun  weatherDao():WeatherDao
}