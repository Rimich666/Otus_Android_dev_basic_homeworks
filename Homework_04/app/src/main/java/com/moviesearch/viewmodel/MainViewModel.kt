package com.moviesearch.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moviesearch.Keys
import com.moviesearch.UI.NewItem
import com.moviesearch.UI.start.InitCashItem
import com.moviesearch.UI.start.RequestedItem
import com.moviesearch.UI.start.StartItem
import com.moviesearch.datasource.database.Favourite
import com.moviesearch.repository.Repository
import com.moviesearch.trace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

const val REQUEST_TITLE = "Запрос страницы"

class MainViewModel(settings: Map<String, *>): ViewModel() {
    var currFragment = settings["startFragment"] as String
    var firstStart: Boolean = settings["firstStart"] as Boolean
    var selectedPosition: Int = settings["selectedPosition"] as Int
    var detailsText: String = ""
    var items: MutableLiveData<MutableList<NewItem>> = MutableLiveData(mutableListOf())
    var favourites: MutableLiveData<MutableList<NewItem>> = MutableLiveData(mutableListOf())

    var changeItem: MutableLiveData<Int> = MutableLiveData(-1)
    var insertFavourite: MutableLiveData<Int> = MutableLiveData(-1)
    var removeFavourite: MutableLiveData<Int> = MutableLiveData(-1)
    var forCancel: MutableLiveData<NewItem> = MutableLiveData()

    var loading: Boolean = false
    var insertItem: MutableLiveData<Int> = MutableLiveData()
    var deletedItem: MutableLiveData<Int> = MutableLiveData()

    class Page(var first: Int, var last: Int, page: Int, val size: Int){
        fun setPos(first: Int){
            this.first = first
            this.last = first + this.size - 1
        }
    }
    var prevP: Page? = null
    var centP: Page? = null
    var nextP: Page? = null

    private var lastPage = 1
    private var firstPage = 1

    var requestedItems: MutableLiveData<MutableList<StartItem>> = MutableLiveData(mutableListOf(

    ))
    var requestedInserted: MutableLiveData<Int> = MutableLiveData()
    var requestedItemChanged: MutableLiveData<Int> = MutableLiveData()

    var responseComplete: MutableLiveData<Boolean> = MutableLiveData(true)
    var atAll: MutableLiveData<Boolean> = MutableLiveData(false)

    private suspend fun deletePage(page: Page){
        withContext(Dispatchers.Main) {
            for(i in(page.last downTo page.first step 1)){
                items.value!!.removeAt(i)
                deletedItem.value = i
            }
        }
    }

    suspend fun getPrevious(){
        firstPage --
        val listPage = Repository.getPage(firstPage)
        if (listPage != null){
            if (prevP != null){
                if (nextP != null){deletePage(nextP!!)}
                lastPage --
                nextP = centP
                centP = prevP
                prevP = null
            }
            prevP = Page(0,listPage.size - 1, firstPage, listPage.size)
            centP!!.setPos(prevP!!.size)
            nextP!!.setPos(centP!!.last + 1)
            for (i in (listPage.size - 1 downTo 0)){
                items.value!!.add(
                    0,
                    NewItem(listPage[i], favourites.value!!.indexOfFirst {
                        listPage[i].idKp == it.idKp
                    } > 0))
                withContext(Dispatchers.Main) {insertItem.value = 0}
            }
        }
        else firstPage ++
        loading = false
    }

    suspend fun getNext(){
        lastPage ++
        val listPage = Repository.getPage(lastPage)
        if (listPage != null){
            if (nextP != null){
                if (prevP != null){ deletePage(prevP!!) }
                firstPage ++
                prevP = centP
                prevP!!.setPos(0)
                centP = nextP
                centP!!.setPos(prevP!!.size)
                nextP = null
            }
            nextP = Page(items.value!!.size, items.value!!.size + listPage.size - 1, lastPage, listPage.size)
            listPage.forEach{ flm ->
                items.value!!.add(
                    items.value!!.size ,
                    NewItem(flm, favourites.value!!.indexOfFirst {
                        flm.idKp == it.idKp
                    } > 0))
                withContext(Dispatchers.Main) {insertItem.value = items.value!!.size}
            }
        }
        else lastPage --
        loading = false
    }

    suspend fun initData(){
        Repository.initData {msg ->
            when{
                msg[0].containsKey(Keys.max) -> {
                    val item = requestedItems.value!![requestedItems.value!!.size - 1] as StartItem.InitCash
                    item.initCash.max = msg[0]["max"] as Int
                    requestedItemChanged.value = requestedItems.value!!.size - 1
                }
                msg[0].containsKey(Keys.progress) -> {
                    val item = requestedItems.value!![requestedItems.value!!.size - 1] as StartItem.InitCash
                    item.initCash.progress = msg[0][Keys.progress] as Int
                    requestedItemChanged.value = requestedItems.value!!.size - 1
                    if (item.initCash.progress == item.initCash.max) atAll.value = true
                }
                msg[0].containsKey((Keys.complete)) -> {
                    responseComplete.value = msg[0][Keys.complete] as Boolean
                }
                msg[0].containsKey(Keys.codeResponse) -> {
                    val pos = msg[0][Keys.requestedPage].toString().toInt() - 1
                    val item = requestedItems.value!![pos] as StartItem.Requested
                    item.requested.action = "$REQUEST_TITLE ${msg[0][Keys.requestedPage]} " +
                            " завершён с кодом: ${msg[0][Keys.codeResponse].toString()}"
                    item.requested.successful = msg[0][Keys.successful] as Boolean
                    requestedItemChanged.value = pos
                }
                msg[0].containsKey((Keys.requested)) -> {
                    val requested = msg[0][Keys.requested] as List<Int>
                    requested.forEach{
                        requestedItems.value!!.add(
                            StartItem.Requested(
                                RequestedItem(
                                    "$REQUEST_TITLE $it",
                                    null
                                )
                            ))
                        requestedInserted.value = requestedItems.value!!.size - 1
                    }
                    requestedItems.value!!.add(
                        StartItem.InitCash(
                            InitCashItem(
                                "Инициализация кэша"
                            )
                        )
                    )
                }
                msg[0].containsKey(Keys.pages) -> {

                    for (i in 1 until msg.size){
                        favourites.value?.indexOfFirst { item -> item.idKp == msg[i]["id"] as Int}
                        msg[i]["licked"] = (favourites.value?.indexOfFirst { item -> item.idKp == msg[i]["id"] as Int}!! > -1)
                        items.value?.add(NewItem(msg[i] as MutableMap<*, *>))
                        Log.d("start", "${trace()} ${msg[i]}")
                    }
                    centP = Page(0, items.value?.size!! - 1, 1, items.value?.size!!)

                }
                msg[0].containsKey(Keys.favour) -> setFavour(msg[0][Keys.favour] as MutableList<Favourite>)
            }
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
            if (liked) Repository.like(item)
            else Repository.dislike(item)
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
        val result: Boolean = Repository.dislike(itemFav)
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
        if (liked) Repository.like(item)
        else Repository.dislike(item)
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