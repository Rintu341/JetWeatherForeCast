package com.example.weatherforcastapp.screen.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforcastapp.model.UnitModel.Unit
import com.example.weatherforcastapp.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.getUnits().distinctUntilChanged()
                .collect{listOfUnits ->
                    if(listOfUnits.isNullOrEmpty())
                    {
                        Log.d("TAG","Empty list")
                    }else{
                        _unitList.value =listOfUnits
                    }

                }
        }
    }


    fun insertUnit(unit: Unit) = viewModelScope.launch { favoriteRepository.insertUnit(unit) }
    fun updateUnit(unit: Unit) = viewModelScope.launch { favoriteRepository.updateUnit(unit) }
    fun deleteUnit(unit: Unit) = viewModelScope.launch { favoriteRepository.deleteUnit(unit) }
    fun deleteAllUnits() = viewModelScope.launch { favoriteRepository.deleteAllUnits() }
}