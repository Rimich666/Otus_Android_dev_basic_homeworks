package com.moviesearch

import android.app.Application
import com.moviesearch.datasource.database.DbObject
import com.moviesearch.datasource.database.FilmDb
import java.util.concurrent.Executors

class App: Application() {
    private var db: FilmDb? = null
    override fun onCreate() {
        super.onCreate()
        Executors.newSingleThreadExecutor().execute(
            Runnable{
                db = DbObject.getDatabase(this)
            }
        )
    }
}