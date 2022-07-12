package com.moviesearch.datasource.database

import androidx.room.*
import kotlinx.coroutines.Deferred

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(film: Film): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPage(page:Page): Long
    @Query("SELECT pages.id FROM pages WHERE pages.num = :page LIMIT 1" )
    fun getPage(page: Int): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavourite(film: Favourite): Long
    @Query("SELECT * FROM favourites")
    fun getFavourites(): MutableList<Favourite>
    @Query("DELETE FROM favourites WHERE favourites.id_kp = :idKp")
    fun deleteFavouriteIdKp(idKp: Int): Int
    @Query("SELECT id FROM favourites WHERE id_kp = :idKp LIMIT 1")
    fun getLiked(idKp: Int): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDeferred(film: DeferredFilm): Long
    @Query("SELECT * FROM deferred_films")
    fun getDeferred(): MutableList<DeferredFilm>
    @Query("DELETE FROM deferred_films WHERE id_kp = :idKp")
    fun deleteDeferred(idKp: Int): Int


    @Query("SELECT * FROM list_films WHERE list_films.page_id = :pageId")
    fun getPageFilms(pageId: Long): MutableList<Film>
}

