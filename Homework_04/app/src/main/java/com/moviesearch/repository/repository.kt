package com.moviesearch.repository

import com.moviesearch.UI.NewItem
import com.moviesearch.datasource.remotedata.LoadData

class repository {

    fun getMovieList(): List<NewItem> {
        return listOf()
    }

    fun loadMovieList(){

    }

    fun initData(progress: (msg: String)->Unit) {
        progress("Привет из репозитория")
        LoadData.initBase(progress)
    }
}