package com.moviesearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainViewModel(setings: Map<String, *>): ViewModel() {
    var currFragment = setings["startFragment"] as String
    var firstStart: Boolean = setings["firstStart"] as Boolean
}

class MainViewModelFactory(private val setings: Map<String, *>): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(setings) as T
        }
        throw IllegalArgumentException("Неизвестный View Model Class")
    }
}