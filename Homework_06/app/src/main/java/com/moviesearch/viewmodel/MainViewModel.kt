package com.moviesearch.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.moviesearch.*
import com.moviesearch.App.Companion.appContext
import com.moviesearch.ui.NewItem
import com.moviesearch.ui.start.InitCashItem
import com.moviesearch.ui.start.RequestedItem
import com.moviesearch.ui.start.StartItem
import com.moviesearch.datasource.database.Favourite
import com.moviesearch.datasource.remotedata.Progress
import com.moviesearch.datasource.remotedata.ResultRequest
import com.moviesearch.repository.Repository
import com.moviesearch.repository.RequestedPages
import com.moviesearch.ui.Details
import com.moviesearch.workers.DetailWorker
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel(settings: MainActivity.Settings): ViewModel() {
    var currFragment = settings.startFragment
    var firstStart: Boolean = settings.firstStart
    var selectedPosition: Int = settings.selectedPosition
    private val mainLifeCycleOwner = settings.owner
    var detailsText: String = ""
    lateinit var details: Details
    var items: MutableLiveData<MutableList<NewItem>> = MutableLiveData(mutableListOf())
    var favourites: MutableLiveData<MutableList<NewItem>> = MutableLiveData(mutableListOf())
    var deferredFilms: MutableLiveData<MutableList<NewItem>> = MutableLiveData(mutableListOf())

    var changeItem: MutableLiveData<Int> = MutableLiveData(-1)
    var insertFavourite: MutableLiveData<Int> = MutableLiveData(-1)
    var removeFavourite: MutableLiveData<Int> = MutableLiveData(-1)
    var changeDeferred: MutableLiveData<Int> = MutableLiveData(-1)
    var deletedDeferred: MutableLiveData<Int> = MutableLiveData(-1)
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

    private val workManager by lazy { WorkManager.getInstance(appContext) }


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

    fun initData(enlightenment: Boolean ){
        val observable = Observable.create<Any>{ emitter ->
            Repository.initData(emitter)
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(object: Observer<Any> {
            override fun onSubscribe(d: Disposable) {

//                TODO("Not yet implemented")
            }

            override fun onNext(t: Any) {
                Log.d(RXTAG, "${trace()} $t")
                when (t) {
                    is ResultRequest -> {
                        Log.d(RXTAG, "${trace()} ResultRequest")
                        val pos = t.page - 1
                        val item = requestedItems.value!![pos] as StartItem.Requested
                        item.requested.action = "$REQUEST_TITLE ${t.page} " +
                                " завершён с кодом: ${t.codeResponse}"
                        item.requested.successful = t.successful
                        requestedItemChanged.value = pos
                    }
                    is RequestedPages -> {
                        println(Thread.currentThread().name)
                        Log.d(RXTAG, "${trace()} RequestedPages")
                        val requested = t.list
                        requested.forEach {
                            requestedItems.value!!.add(
                                StartItem.Requested(
                                    RequestedItem(
                                        "$REQUEST_TITLE $it",
                                        null
                                    )
                                )
                            )
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
                    is Progress ->{
                        val item = requestedItems.value!![requestedItems.value!!.size - 1] as StartItem.InitCash
                        item.initCash.max = t.max
                        requestedItemChanged.value = requestedItems.value!!.size - 1
                    }
//                  TODO("Not yet implemented")
                }
            }
            override fun onError(e: Throwable) {
//                TODO("Not yet implemented")
            }

            override fun onComplete() {
//                TODO("Not yet implemented")
            }

        })
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
                        msg[i]["licked"] = favourites.value!!.indexOfFirst { item -> item.idKp == msg[i]["id"] as Int }!! > -1
                        msg[i]["deferred"] = deferredFilms.value!!.indexOfFirst { item -> item.idKp == msg[i]["id"] as Int } > -1
                        items.value?.add(NewItem(msg[i] as MutableMap<*, *>))
                    }
                    centP = Page(0, items.value?.size!! - 1, 1, items.value?.size!!)

                }
                msg[0].containsKey(Keys.favour) -> setFavour(msg[0][Keys.favour] as MutableList<Favourite>)
            }
        }
    }

    suspend fun getDetails(idKp:Int, infl: () -> Any ){
        Repository.getDetails(idKp) { msg ->
            detailsText = msg
            details = parseJson(msg, Details()) as Details

            //val map: MutableMap<String, Any> = parseJson(detailsText)
            infl()
        }
    }

    private fun setFavour(fav: MutableList<Favourite>){
        fav.forEach{ favourites.value?.add(NewItem(it)) }
    }

    suspend fun cancelLiked(){
        val item = forCancel.value!!
        val liked = !item.liked
        val result: Boolean =
            if (liked) Repository.like(item)
            else Repository.dislike(item)
        if (result){
            if (changeItem.value!! > -1){
                withContext(Dispatchers.Main){
                    items.value!![changeItem.value!!].liked = liked
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

    private suspend fun observationOfWork(id: UUID){
        val liveWork = workManager.getWorkInfoByIdLiveData(id)
        withContext(Dispatchers.Main){
            liveWork.observe(mainLifeCycleOwner){ work ->
                if (work == null) return@observe
                Log.d(WMTAG, "${trace()} подписка на работу $work")
                if (work.state != WorkInfo.State.ENQUEUED) {
                    var pos = items.value!!.indexOfFirst { item -> item.idWork == work.id.toString()}
                    if (pos > -1) {
                        Log.d(WMTAG, "${trace()} ${work.state} надо чё то менять")
                        items.value!![pos].apply {
                            idWork = ""
                            deferDateTime = ""
                            deferred = false
                        }
                        changeItem.value = pos
                    }
                    pos = deferredFilms.value!!.indexOfFirst { item -> item.idWork == work.id.toString() }
                    if (pos > -1) {
                        deferredFilms.value!!.removeAt(pos)
                        deletedDeferred.value = pos
                    }
                    liveWork.removeObservers(mainLifeCycleOwner)
                }
            }
        }
    }

    private suspend fun syncDeferred (works: List<WorkInfo>){
        for (i in deferredFilms.value!!.size - 1 downTo 0 ){
            if ( works.indexOfFirst {
                        work -> deferredFilms.value!![i].idWork == work.id.toString() &&
                        work.state != WorkInfo.State.ENQUEUED
                } == -1
            ) {
                Repository.removeDeferred(deferredFilms.value!![i].idKp)
                deferredFilms.value!!.removeAt(i)
            }
        }
        works.forEach { work -> if ( work.state == WorkInfo.State.ENQUEUED){
                val item = NewItem(Repository.getDeferred(work.id.toString()))
                if( deferredFilms.value!!.indexOfFirst { it.idWork == work.id.toString()} == -1) deferredFilms.value!!.add(item)
                val pos = items.value!!.indexOfFirst { it.idKp == item.idKp }
                if (pos > -1){
                    items.value!![pos].deferred = true
                    items.value!![pos].idWork = work.id.toString()
                    changeItem.value = pos
                }
            }
        }
    }

    suspend fun observationOfWorks(){
        val works = workManager.getWorkInfosByTag(WMTAG).await()
        syncDeferred(works)

        Log.d(WMTAG, "${trace()} обсерватория $works")
        works.forEach {workInfo ->
            val work = workManager.getWorkInfoById(workInfo.id).await()
            if (work.state == WorkInfo.State.ENQUEUED){
                observationOfWork(workInfo.id)
            }
        }
    }

    suspend fun cancelWork(item: NewItem, pos: Int){
        workManager.cancelUniqueWork(item.idKp.toString())
        Repository.removeDeferred(item.idKp)
        deferredFilms.value!!.removeAt(pos)
        withContext(Dispatchers.Main){ deletedDeferred.value = pos }
        val itpos = items.value!!.indexOfFirst { it.idKp == item.idKp }
        if(itpos > -1){
            items.value!![itpos].apply {
                deferred = false
                idWork = ""
                deferDateTime = ""
            }
            withContext(Dispatchers.Main){ changeItem.value = itpos }
        }
    }

    suspend fun addDeferred(item: NewItem, pos: Int, dateTime: String){
        val now = LocalDateTime.now()
        val alarm = LocalDateTime.parse(dateTime)
        val interval = if (alarm > now) Duration.between(now, alarm).toMinutes()
                    else 1
        val workRequest = OneTimeWorkRequestBuilder<DetailWorker>()
            .setInitialDelay(interval, TimeUnit.MINUTES)
            .addTag(WMTAG)
            .setInputData(item.workData())
            .build()
        workManager.enqueueUniqueWork(item.idKp.toString(), ExistingWorkPolicy.REPLACE, workRequest)

        item.deferred = true
        item.deferDateTime = dateTime
        item.idWork = workRequest.id.toString()
        observationOfWork(workRequest.id)
        Log.d(WMTAG, "${trace()} постановка ${workRequest.id}")

        val antPos = currFragment.antonymDefr().list!!.indexOfFirst { litem -> litem.idKp == item.idKp  }
        if ( antPos > -1) {
            currFragment.antonymDefr().list!![antPos] = item.copy()
        }

        withContext(Dispatchers.Main){
            when(currFragment){
                Frags.LIST -> {
                    changeItem.value = pos
                    if (antPos == -1) {
                        deferredFilms.value!!.add(item.copy())
                    }
                }
                Frags.DEFER ->changeDeferred.value = pos
                else ->{}
            }
        }
        Repository.insertWork(item)
    }
}

class MainViewModelFactory(private val settings: MainActivity.Settings): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(settings) as T
        }
        throw IllegalArgumentException("Неизвестный View Model Class")
    }
}