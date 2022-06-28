package com.moviesearch.datasource.database;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0007J\u001a\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u000bJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0016\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/moviesearch/datasource/database/QueryDb;", "", "()V", "getPage", "", "Lcom/moviesearch/datasource/database/Film;", "page", "", "insertFilm", "", "itemMap", "", "", "insertPage", "", "isLiked", "isView", "idKp", "app_debug"})
public final class QueryDb {
    @org.jetbrains.annotations.NotNull()
    public static final com.moviesearch.datasource.database.QueryDb INSTANCE = null;
    
    private QueryDb() {
        super();
    }
    
    private final long insertPage(int page) {
        return 0L;
    }
    
    public final boolean insertFilm(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Object> itemMap) {
        return false;
    }
    
    public final boolean isLiked(boolean isView, int idKp) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.moviesearch.datasource.database.Film> getPage(int page) {
        return null;
    }
}