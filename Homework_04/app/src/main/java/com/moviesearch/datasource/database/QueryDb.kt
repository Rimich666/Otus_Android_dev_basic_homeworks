package com.moviesearch.datasource.database

import android.util.Log
import com.moviesearch.App.Companion.db
import com.moviesearch.trace
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.RENDEZVOUS

object QueryDb {
    suspend fun insertFilms(itemMaps: MutableList<MutableMap<String,Any>>, channel: Channel<Long>?) = coroutineScope{
        val itemMap = itemMaps[0]
        val pageId: Deferred<Long> = async {
            var pId = db!!.filmDao().getPage(itemMap["page"] as Int)
            if (pId == 0L) pId = db!!.filmDao().insertPage(Page(itemMap["page"] as Int))
            return@async pId
        }
        val idPage = pageId.await()

        for (i in 1 until itemMaps.size){
            Log.d("insertFilm", "${trace()} " +
                    "pageId = $idPage " +
                    "page[$i] = ${itemMaps[i]["page"]}, " +
                    "idKp[$i] = ${itemMaps[i]["id"]}")
            launch { insertFilm(itemMaps[i], idPage, channel) }
        }
    }


    suspend fun insertFilm(itemMap: Map<String, Any>, idPage: Long, channel: Channel<Long>?){
        var res: Long = 0
        Log.d("insertFilm", "${trace()} " +
                "pageId = $idPage " +
                "page = ${itemMap["page"]}, " +
                "idKp = ${itemMap["id"]}")
        if( idPage != 0L){
            res = db?.filmDao()?.insertFilm(Film(itemMap, idPage))!!
            Log.d("insertFilm", "${trace()} " +
                    "pageId = $idPage " +
                    "page = ${itemMap["page"]}, " +
                    "idKp = ${itemMap["id"]}, " +
                    "res = $res")
        }
        channel?.send(res)
    }

    fun isLiked(isView: Boolean, idKp: Int): Boolean{
        if(!isView) return false
        val liked = db?.filmDao()?.getLiked(idKp)
        return liked != 0L
    }

    fun getPage(page: Int): MutableList<Film>? {
        val pId = db?.filmDao()?.getPage(page)
        return if (pId == 0L) null
        else db?.filmDao()?.getPageFilms(pId!!)
    }

    fun checkPages(pages: MutableList<Int>): MutableList<Int>{
        val chList = mutableListOf<Int>()
        pages.forEach{ if (db?.filmDao()?.getPage(it) == 0L) chList.add(it)}
        return chList
    }
}