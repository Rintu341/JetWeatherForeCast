package com.example.weatherforcastapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherforcastapp.model.favoritesModel.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query(value = "SELECT * FROM FAV_TBL")
    fun getFavorites(): Flow<List<Favorite>>

    @Query(value = "SELECT * FROM FAV_TBL WHERE city = :city")
    suspend fun  getFavById(city:String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE FROM FAV_TBL")
    suspend fun deleteAllFavorite()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}