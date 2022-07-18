package com.moviesearch

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.moviesearch.datasource.database.DbObject
import com.moviesearch.datasource.database.FilmDb
import com.moviesearch.workers.Notification
import java.util.concurrent.Executors

class App: Application() {
    override fun onCreate() {
        super<Application>.onCreate()
        appContext = applicationContext
        Executors.newSingleThreadExecutor().execute {
            db = DbObject.getDatabase(appContext)
            Log.d("TraceOfBase", "А при старте что? ${db.toString()}")
        }
        Notification.createNotificationChanel()
    }

    companion object{
        var db: FilmDb? = null
        lateinit var appContext: Context

    }
}