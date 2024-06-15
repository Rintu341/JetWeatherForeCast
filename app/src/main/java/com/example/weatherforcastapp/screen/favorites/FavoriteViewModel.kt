package com.example.weatherforcastapp.screen.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforcastapp.data.DataOrException
import com.example.weatherforcastapp.model.favoritesModel.Favorite
import com.example.weatherforcastapp.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository : FavoriteRepository
) : ViewModel(){
    private val _favoriteList = MutableStateFlow<DataOrException<List<Favorite>, Boolean, Exception>>(DataOrException())
    val favoriteList: StateFlow<DataOrException<List<Favorite>, Boolean, Exception>> = _favoriteList.asStateFlow()

    init {
        try {
            _favoriteList.value.loading = true
            viewModelScope.launch (Dispatchers.IO){
                 favoriteRepository.getFavorites()
                     .distinctUntilChanged()
                    .collect{ list->
                        if(list.isNotEmpty())
                        {
                            _favoriteList.value.data = list
                            _favoriteList.value.loading = false
                            Log.d("list","${_favoriteList.value.data}")
                        }else{
                            Log.d("list","empty")
                        }
                    }

            }
        }catch (e: Exception)
        {
            _favoriteList.value.loading = false
            _favoriteList.value.e = e
            Log.d("exception","$e")
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) { favoriteRepository.insertFavorite(favorite) }
    fun updateFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) { favoriteRepository.updateFavorite(favorite) }
    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) { favoriteRepository.deleteFavorite(favorite) }
    fun deleteAllFavorite() = viewModelScope.launch(Dispatchers.IO) { favoriteRepository.deleteAllFavorite() }


}