package com.moviesearch.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moviesearch.UI.NewItem
import com.moviesearch.datasource.database.Favourite
import com.moviesearch.repository.Repository
import com.moviesearch.trace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    var changeItem: MutableLiveData<Int> = MutableLiveData(-1)
    var insertFavourite: MutableLiveData<Int> = MutableLiveData(-1)
    var removeFavourite: MutableLiveData<Int> = MutableLiveData(-1)
    var forCancel: MutableLiveData<NewItem> = MutableLiveData()

    suspend fun initData(prog: (complete: Boolean)->Unit){
        Repository().initData {msg ->
            if (msg.containsKey("max")) maxProgress.value = msg["max"] as Int
            if (msg.containsKey("progress")) progress.value = msg["progress"] as Int
            if (msg.containsKey(("complete"))) prog(msg["complete"] as Boolean)
            if (msg.containsKey("item")) items.value?.add(NewItem(msg["item"] as MutableMap<*, *>))
            if (msg.containsKey("favour")) setFavour(msg["favour"] as MutableList<Favourite>)
        }
    }

    private fun setFavour(fav: MutableList<Favourite>){
        fav.forEach{favourites.value?.add(NewItem(it))}
    }

    suspend fun cancelLiked(){
        Log.d("cancelLiked", "${trace()} remove or add favourite")
        Log.d("cancelLiked", "${trace()} removeFavourite = ${removeFavourite.value}")
        val item = forCancel.value!!
        val liked = !item.liked
        val result: Boolean =
            if (liked) Repository().like(item)
            else Repository().dislike(item)
        Log.d("cancelLiked", "${trace()} result = $result")
        if (result){
            withContext(Dispatchers.Main){
                items.value!![changeItem.value!!].liked = liked
                Log.d("cancelLiked", "${trace()} changeItem = ${changeItem.value}")
                changeItem.value = changeItem.value
            }
            if (liked){
                withContext(Dispatchers.Main){
                    favourites.value!!.add(removeFavourite.value!!,item)
                    insertFavourite.value = removeFavourite.value
                }
            }
            else favourites.value!!.remove(item)
        }
//        Log.d("changeLiked","${trace()} сдушалка")
    }

    suspend fun dislike(itemFav: NewItem, posFav: Int){
        val itemsLoc = items.value!!
        val pos = itemsLoc.indexOfFirst { it.idKp == itemFav.idKp }
        val result: Boolean = Repository().dislike(itemFav)
        if (result) {
            if (pos > -1) {
                itemsLoc[pos].liked = false
                withContext(Dispatchers.Main){changeItem.value = pos}
            }
            withContext(Dispatchers.Main){
                forCancel.value = itemFav
                favourites.value?.remove(itemFav)
                removeFavourite.value = posFav
            }
        }
    }


    suspend fun removeOrAddFavour(item: NewItem, pos: Int){
        Log.d("changeLiked", "${trace()} remove or add favourite")
        val liked = !item.liked
        val result: Boolean =
        if (liked) Repository().like(item)
        else Repository().dislike(item)
        if (result) {
            item.liked = liked
            if (liked) favourites.value?.add(item)
            else {
                withContext(Dispatchers.Main){
                    removeFavourite.value = favourites.value?.indexOfFirst { it.idKp == item.idKp }!!
                    favourites.value?.removeAt(removeFavourite.value!!)
                }
            }
            withContext(Dispatchers.Main){
                changeItem.value = pos
                forCancel.value = item
            }
        }
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