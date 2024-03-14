package com.ryanpatrick.mathhammer40k.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ryanpatrick.mathhammer40k.Screens

class NavViewModel: ViewModel() {
    private val _currentScreen: MutableState<Screens> = mutableStateOf(Screens.SimScreen)
    val currentScreen: MutableState<Screens> get() = _currentScreen

    fun setCurrentScreen(screen: Screens){
        _currentScreen.value = screen
    }
}