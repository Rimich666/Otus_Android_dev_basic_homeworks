package com.example.helloworld.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Film::class], version = 1)
abstract class FilmDb: RoomDatabase() {
    abstract fun filmDao(): FilmDao
}



