package com.moviesearch.datasource.database;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\tR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/moviesearch/datasource/database/DbObject;", "", "()V", "INSTANCE", "Lcom/moviesearch/datasource/database/FilmDb;", "destroyInstance", "", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
public final class DbObject {
    @org.jetbrains.annotations.NotNull()
    public static final com.moviesearch.datasource.database.DbObject INSTANCE = null;
    private static com.moviesearch.datasource.database.FilmDb INSTANCE;
    
    private DbObject() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.moviesearch.datasource.database.FilmDb getDatabase(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    public final void destroyInstance() {
    }
}