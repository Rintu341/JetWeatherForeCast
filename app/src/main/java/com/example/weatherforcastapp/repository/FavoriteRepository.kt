package com.example.weatherforcastapp.repository

import com.example.weatherforcastapp.data.WeatherDao
import com.example.weatherforcastapp.model.favoritesModel.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val weatherDao: WeatherDao
) {
     suspend fun  insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
     suspend fun  updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
     suspend fun  deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
     suspend fun  deleteAllFavorite() = weatherDao.deleteAllFavorite()
     fun  getFavorites():Flow<List<Favorite>> = weatherDao.getFavorites().flowOn(Dispatchers.IO).conflate()
     suspend fun getFavByID(city:String):Favorite = weatherDao.getFavById(city)
}