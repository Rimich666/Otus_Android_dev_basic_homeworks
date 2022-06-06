package com.moviesearch.datasource.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface FilmDao {
    @Insert
    fun insert(film: Film)
}