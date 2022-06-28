package com.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviesearch.UI.start.StartItem

class StartViewModel(): ViewModel() {
    var items: MutableLiveData<MutableList<StartItem>> = MutableLiveData()

    fun setItems(requested: List<Int>){
        requested.forEach{ items.value!!.add(
            StartItem("Запрос страницы $it", ""))}
    }
}