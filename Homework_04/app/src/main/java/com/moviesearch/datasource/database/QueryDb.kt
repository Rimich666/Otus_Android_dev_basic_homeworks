package com.moviesearch.datasource.database

import android.util.Log
import com.moviesearch.App.Companion.db
import com.moviesearch.trace

object QueryDb {

    private fun insertPage(page: Int): Long{
        var pId = db?.filmDao()?.getPage(page)
        if (pId == 0L) pId = db?.filmDao()?.insertPage(Page(page))
        return pId!!
    }

    fun insertFilm(itemMap: MutableMap<String, Any>): Boolean{
        val pageId = insertPage(itemMap["page"] as Int)
        var res: Long? = null
        Log.d("insertFilm", "${trace()} pageId = $pageId")
        if( pageId != 0L){
            val film = Film(itemMap, pageId)
            res = db?.filmDao()?.insertFilm(film)
        }
        return res != 0L
    }

    fun isLiked(isView: Boolean, idKp: Int): Boolean{
        if(!isView) return false
        val liked = db?.filmDao()?.getLiked(idKp)
        return liked != 0L
    }
}