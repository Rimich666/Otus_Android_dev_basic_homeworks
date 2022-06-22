package com.moviesearch.datasource.database

import com.moviesearch.App.Companion.db

object QueryDb {

    private fun insertPage(page: Int): Long?{
        var pId = db?.filmDao()?.getPage(page)
        if (pId == null) pId = db?.filmDao()?.insertPage(Page(page))
        return pId
    }

    fun insertFilm(itemMap: MutableMap<String, Any>): Boolean{
        val pageId = insertPage(itemMap["page"] as Int)
        var res: Long? = null
        if( pageId != null){
            val film = Film(itemMap, pageId)
            res = db?.filmDao()?.insertFilm(film)
        }
        return res != null
    }

    fun isLiked(isView: Boolean, idKp: Int): Boolean{
        if(!isView) return false
        val liked = db?.filmDao()?.getLiked(idKp)
        return liked != null
    }
}