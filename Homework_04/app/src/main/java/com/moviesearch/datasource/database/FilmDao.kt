package com.moviesearch.datasource.database

import androidx.room.*

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(film: Film): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPage(page:Page): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavourite(film: Favourite): Long
    @Query("SELECT * FROM favourites")
    fun getFavourites(): MutableList<Favourite>
    @Query("DELETE FROM favourites WHERE favourites.id_kp = :idKp")
    fun deleteFavouriteIdKp(idKp: Int): Int


    @Query("SELECT * FROM list_films WHERE list_films.page_id = :pageId")
    fun getPage(pageId: Int): MutableList<Film>
}

