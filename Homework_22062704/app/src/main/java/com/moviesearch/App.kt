package com.moviesearch

import android.app.Application
import android.content.Context
import android.util.Log
import com.moviesearch.datasource.database.DbObject
import com.moviesearch.datasource.database.FilmDb
import com.moviesearch.datasource.remotedata.LoadData
import java.util.concurrent.Executors

class App: Application() {
    private var app: Application = this

    fun getApp(): Application{
        Log.d("TraceOfBase", "Таки запросил app ${app.toString()}")
        return app
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        Log.d("TraceOfBase", "А это Апп ${app.toString()}")
        Executors.newSingleThreadExecutor().execute(
            Runnable{
                db = DbObject.getDatabase(this.applicationContext)
                Log.d("TraceOfBase", "А при старте что? ${db.toString()}")
            }
        )
    }

    companion object{
        var db: FilmDb? = null
        fun getDatabase(): FilmDb? {
            Log.d("TraceOfBase", "Таки запросил ${db.toString()}")
            return db
        }
    }

}