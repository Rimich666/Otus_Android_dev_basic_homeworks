package com.moviesearch.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Film::class, Page::class, Favourite::class], version = 1)
abstract class FilmDb: RoomDatabase() {
    abstract fun filmDao(): FilmDao
}