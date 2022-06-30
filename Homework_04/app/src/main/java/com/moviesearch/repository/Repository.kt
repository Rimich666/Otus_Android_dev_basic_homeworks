package com.moviesearch.repository

import android.util.Log
import com.moviesearch.App.Companion.db
import com.moviesearch.UI.NewItem
import com.moviesearch.datasource.database.Favourite
import com.moviesearch.datasource.database.Film
import com.moviesearch.datasource.database.QueryDb
import com.moviesearch.datasource.database.QueryDb.insertFilm
import com.moviesearch.datasource.database.QueryDb.insertFilms
import com.moviesearch.datasource.database.QueryDb.isLiked
import com.moviesearch.datasource.remotedata.LoadData
import com.moviesearch.trace
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

object Repository {
    private var pagesCount: Int = 0
    private const val SIZEOF = 5
    private var progressI: Int = 0

    suspend fun getPage(page: Int): MutableList<Film>? {
        Log.d("scrolling", "${trace()} pagesCount = $pagesCount")
        if (page < 1 || page > pagesCount) return null
        CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO){
            var pages = mutableListOf<Int>()
            for(i in page - SIZEOF .. page + SIZEOF) if (!(i < 1 || i > pagesCount)) pages.add(i)
            Log.d("scrolling", "${trace()} pages до $pages")
            pages = QueryDb.checkPages(pages)
            Log.d("scrolling", "${trace()} pages после $pages")
            LoadData.loadPages(pages){msg ->
                if (msg[0].containsKey("pages"))
                    launch { insertFilms(msg, null) }
                }
        }
        val res = QueryDb.getPage(page)
        if (res == null)
            Log.d("scrolling", "${trace()} нет страницы $page")
        else
            Log.d("scrolling", "${trace()} есть страница $page")
        return QueryDb.getPage(page)
    }

    suspend fun initData(progress: (msg: MutableList<MutableMap<String,Any>>)->Unit) = coroutineScope{
        val currentPage: Int = 1
        val pages = mutableListOf<Int>()
        for(i in currentPage .. SIZEOF + 1 ) pages.add(i)
        withContext(Dispatchers.Main) {
            progress(mutableListOf(mutableMapOf("requested" to pages))) }
        Log.d("pages", "${trace()} pages: $pages")
        launch(Dispatchers.IO) {
            LoadData.loadPages(pages) { msg ->
                var channel: Channel<Long>? = null
                if (msg[0].containsKey("pages")) {
                    channel = Channel(Channel.RENDEZVOUS)
                    launch { insertFilms(msg, channel) }
                    if (msg[0]["page"] == currentPage) {
                        pagesCount = msg[0]["pages"] as Int
                        withContext(Dispatchers.Main) { progress(msg) }
                    }
                }
                else withContext(Dispatchers.Main) { progress(msg) }
                if (channel != null)
                    repeat(msg.size - 1) {
                        val inc = channel.receive()
                        progressI++
                        withContext(Dispatchers.Main) {
                            progress(mutableListOf(mutableMapOf("progress" to progressI)))
                        }
                    }
            }
        }
        val favour: Deferred<MutableList<Favourite>> = async{ db?.filmDao()?.getFavourites()!!}
        progress(mutableListOf(mutableMapOf("favour" to favour.await())))
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