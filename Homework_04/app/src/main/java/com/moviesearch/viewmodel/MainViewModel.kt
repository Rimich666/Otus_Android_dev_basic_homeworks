package com.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moviesearch.UI.NewItem
import com.moviesearch.repository.Repository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.IllegalArgumentException

class MainViewModel(settings: Map<String, *>): ViewModel() {
    var currFragment = settings["startFragment"] as String
    var firstStart: Boolean = settings["firstStart"] as Boolean
    var selectedPosition: Int = settings["selectedPosition"] as Int
    var detailsText: String = ""
    var progress: MutableLiveData<Int> = MutableLiveData(settings["progress"] as Int)
    var maxProgress: MutableLiveData<Int> = MutableLiveData(0)
    var items: MutableLiveData<MutableList<NewItem>> = MutableLiveData(mutableListOf())
    var favourites: MutableLiveData<MutableList<NewItem>> = MutableLiveData(mutableListOf())

    fun dislike(pos: Int){
        val itemsL = items.value!!
        val favour = favourites.value!!
        val item = favour[pos]
        val itemPos = itemsL.indexOf(item)
        if (itemPos > -1) itemsL[itemPos].liked = false
        favour.removeAt(pos)
    }

    suspend fun initData(prog: (complete: Boolean)->Unit){
        Repository().initData{msg ->
            if (msg.containsKey("max")) maxProgress.value = msg["max"] as Int
            if (msg.containsKey("progress")) progress.value = msg["progress"] as Int
            if (msg.containsKey(("complete"))) prog(msg["complete"] as Boolean)
            if (msg.containsKey("item")) items.value?.add(NewItem(msg["item"] as MutableMap<*, *>))
        }
    }

    suspend fun removeOrAddFavour(pos: Int, liked: Boolean, item: NewItem){
        val result: Boolean =
        if (liked) Repository().like(item)
        else Repository().dislike(item)
    }


    fun changeFavourites(item: NewItem, pos: Int){
        val favour = favourites.value!!
        if (item.liked){ favour.removeAt(pos) }
        else{favour.add(item)}
        item.liked = !item.liked
    }
}

class MainViewModelFactory(private val setings: Map<String, *>): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(setings) as T
        }
        throw IllegalArgumentException("Неизвестный View Model Class")
    }
}