package com.example.weatherforcastapp.permissions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/*
In this code snippet, the location property is declared as a State<LocationData?>.
 Unlike MutableState, State is immutable, meaning once it's initialized,
 its value cannot be changed directly.
 It's typically used when you want to expose state to external
 components but prevent them from modifying it directly.
  Any changes to the state would require updating _location internally.
 */
class LocationViewModel : ViewModel() {
    private val _location = mutableStateOf<LocationData?>(null)
    val location : State<LocationData?> = _location

    fun updateLocation(newLocation: LocationData)
    {
        _location.value = newLocation
    }

}