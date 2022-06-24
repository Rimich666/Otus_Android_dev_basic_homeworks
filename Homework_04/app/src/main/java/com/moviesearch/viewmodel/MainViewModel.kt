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

    var loading: Boolean = false
    var insertItem: MutableLiveData<Int> = MutableLiveData()
    var deletedItem: MutableLiveData<Int> = MutableLiveData()
    //var deletedItems: MutableLiveData<Page> = MutableLiveData()
    //var insertPage: MutableLiveData<Page> = MutableLiveData()

    class Page(var first: Int, var last: Int, page: Int, val size: Int)
    var prevP: Page? = null
    var currP: Page? = null
    var nextP: Page? = null

    private var lastPage = 1
    private var firstPage = 1

    suspend fun getNext(){
        Log.d("paging", "${trace()} ViewModel.getNext()")
        lastPage ++
        Log.d("scrolling", "${trace()} lastPage = $lastPage")
        val listPage = Repository().getNext(lastPage)
        Log.d("scrolling", "${trace()} listPage = $listPage")

        if (nextP != null){
            deletePage(currP!!)
        }

        if (nextP == null){
//               nextP = Page(items.value!!.size, items.value!!.size + 50, lastPage, 50)
            nextP = Page(items.value!!.size, items.value!!.size + listPage!!.size - 1, lastPage, listPage.size)
            listPage!!.forEach{
                    val flm = it
                items.value!!.add(
                    items.value!!.size ,
                    NewItem(flm, favourites.value!!.indexOfFirst {
                        flm.idKp == it.idKp
                    } > 0))
                withContext(Dispatchers.Main) {insertItem.value = items.value!!.size}
            }
        }
        else
            Log.d("paging", "${trace()} Это залёт воин: nextP не null")


        loading = false
        Log.d("scrolling", "${trace()} nextP: $nextP")
    }

    private suspend fun deletePage(page: Page){
        withContext(Dispatchers.Main) {
            for(i in(currP!!.size downTo 0 step 1)){
                Log.d("scrolling", "${trace()} delete $i")
                items.value!!.removeAt(i)
                deletedItem.value = i
            }
        }
        //withContext(Dispatchers.Main) { deletedItems.value = currP }
        Log.d("scrolling", "${trace()} nextP: $nextP")
        currP = nextP
        currP!!.first = 0
        currP!!.last = currP!!.size - 1
        nextP = null
    }

    suspend fun initData(prog: (complete: Boolean)->Unit){
        Repository().initData {msg ->
            if (msg.containsKey("max")) maxProgress.value = msg["max"] as Int
            if (msg.containsKey("progress")) progress.value = msg["progress"] as Int
            if (msg.containsKey(("complete"))) {
                currP = Page(0, items.value?.size!! - 1, 1, items.value?.size!!)
                prog(msg["complete"] as Boolean)
            }
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
            if (changeItem.value!! > -1){
                withContext(Dispatchers.Main){
                    items.value!![changeItem.value!!].liked = liked
                    Log.d("cancelLiked", "${trace()} changeItem = ${changeItem.value}")
                    changeItem.value = changeItem.value
                }
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
                forCancel.value?.liked = false
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