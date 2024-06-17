package com.example.weatherforcastapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherforcastapp.model.UnitModel.Unit
import com.example.weatherforcastapp.model.favoritesModel.Favorite

@Database(version = 2, entities =[Favorite::class,Unit::class], exportSchema = false )
abstract class WeatherDatabase() : RoomDatabase(){
    abstract fun  weatherDao():WeatherDao
}