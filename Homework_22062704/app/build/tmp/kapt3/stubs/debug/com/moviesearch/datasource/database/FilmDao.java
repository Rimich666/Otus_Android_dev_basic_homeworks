package com.moviesearch.datasource.database;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\'J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\'J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0003H\'J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0003H\'J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00062\u0006\u0010\u000e\u001a\u00020\tH\'J\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0007H\'J\u0010\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\rH\'J\u0010\u0010\u0012\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0013H\'\u00a8\u0006\u0014"}, d2 = {"Lcom/moviesearch/datasource/database/FilmDao;", "", "deleteFavouriteIdKp", "", "idKp", "getFavourites", "", "Lcom/moviesearch/datasource/database/Favourite;", "getLiked", "", "getPage", "page", "getPageFilms", "Lcom/moviesearch/datasource/database/Film;", "pageId", "insertFavourite", "film", "insertFilm", "insertPage", "Lcom/moviesearch/datasource/database/Page;", "app_debug"})
public abstract interface FilmDao {
    
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract long insertFilm(@org.jetbrains.annotations.NotNull()
    com.moviesearch.datasource.database.Film film);
    
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract long insertPage(@org.jetbrains.annotations.NotNull()
    com.moviesearch.datasource.database.Page page);
    
    @androidx.room.Query(value = "SELECT pages.id FROM pages WHERE pages.num = :page LIMIT 1")
    public abstract long getPage(int page);
    
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.IGNORE)
    public abstract long insertFavourite(@org.jetbrains.annotations.NotNull()
    com.moviesearch.datasource.database.Favourite film);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM favourites")
    public abstract java.util.List<com.moviesearch.datasource.database.Favourite> getFavourites();
    
    @androidx.room.Query(value = "DELETE FROM favourites WHERE favourites.id_kp = :idKp")
    public abstract int deleteFavouriteIdKp(int idKp);
    
    @androidx.room.Query(value = "SELECT id FROM favourites WHERE id_kp = :idKp LIMIT 1")
    public abstract long getLiked(int idKp);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM list_films WHERE list_films.page_id = :pageId")
    public abstract java.util.List<com.moviesearch.datasource.database.Film> getPageFilms(long pageId);
}