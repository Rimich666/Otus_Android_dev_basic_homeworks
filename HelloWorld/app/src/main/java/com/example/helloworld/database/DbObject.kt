package com.example.helloworld.database

import android.content.Context
import androidx.room.Room

object DbObject
{
    private var INSTANCE: FilmDb? = null

    fun getDatabase(context: Context): FilmDb?{
        if (INSTANCE == null){
            synchronized(FilmDb::class){
                INSTANCE = Room.databaseBuilder(
                    context,
                    FilmDb::class.java,
                    "movies.db"
                ).build()
            }
        }
        return INSTANCE
    }

    fun destroyInstance(){
        INSTANCE?.close()
        INSTANCE = null
    }
}