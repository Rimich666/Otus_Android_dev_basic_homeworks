package com.moviesearch.repository

import android.util.Log
import com.moviesearch.App.Companion.db
import com.moviesearch.UI.NewItem
import com.moviesearch.datasource.database.Favourite
import com.moviesearch.datasource.database.QueryDb.insertFilm
import com.moviesearch.datasource.database.QueryDb.isLiked
import com.moviesearch.datasource.remotedata.LoadData
import com.moviesearch.trace
import kotlinx.coroutines.*

class Repository {
    var currentPage: Int = 1
    var pages = listOf<Int>(1,2,3)

    fun getMovieList(): List<NewItem> {
        return listOf()
    }

    fun loadMovieList(){

    }

    suspend fun initData(progress: (msg: Map<String,*>)->Unit) = coroutineScope{
        launch(Dispatchers.IO) {
            LoadData.loadPages(pages, currentPage) { msg ->
                if (msg.containsKey("item")){
                    val item = msg["item"] as MutableMap<String, Any>
                    val isView = item["page"] as Int == currentPage
                    val ins: Deferred<Boolean> = async { insertFilm(item) }
                    val liked: Boolean =
                        withContext(Dispatchers.Default) {
                            isLiked(
                                isView,
                                item["id"] as Int
                            )
                        }
                    if (ins.await() && isView){
                        item["liked"] = liked
                        withContext(Dispatchers.Main){progress(mutableMapOf("item" to item))}
                    }
                }
                else withContext(Dispatchers.Main){progress(msg)}
            }
        }
        val favour: Deferred<MutableList<Favourite>> = async{ db?.filmDao()?.getFavourites()!!}
        progress(mapOf("favour" to favour.await()))
    }

    suspend fun getDetails(id: Int, takeDetails: (msg: String)->Unit) = coroutineScope{
        launch(Dispatchers.IO) {
            LoadData.getDetail(id){msg ->
                withContext(Dispatchers.Main){
                    takeDetails(msg)
                }
            }
        }
    }

    suspend fun dislike(item: NewItem):Boolean = coroutineScope{
        val res: Deferred<Int?> = async { db?.filmDao()?.deleteFavouriteIdKp(item.idKp) }
        return@coroutineScope res.await()!! > 0L
    }

    suspend fun like(item: NewItem): Boolean = coroutineScope{
        Log.d("cancelLiked", "${trace()} item = ${item.pictures}")
        val res: Deferred<Long?> = async { db?.filmDao()?.insertFavourite(Favourite(item)) }
        return@coroutineScope res.await()!=0L
    }

}