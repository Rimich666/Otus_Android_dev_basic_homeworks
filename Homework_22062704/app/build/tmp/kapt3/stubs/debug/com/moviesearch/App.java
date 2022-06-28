package com.moviesearch;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0004\u001a\u00020\u0001J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/moviesearch/App;", "Landroid/app/Application;", "()V", "app", "getApp", "onCreate", "", "Companion", "app_debug"})
public final class App extends android.app.Application {
    private android.app.Application app;
    @org.jetbrains.annotations.NotNull()
    public static final com.moviesearch.App.Companion Companion = null;
    @org.jetbrains.annotations.Nullable()
    private static com.moviesearch.datasource.database.FilmDb db;
    
    public App() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.Application getApp() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u0004\u0018\u00010\u0004R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\n"}, d2 = {"Lcom/moviesearch/App$Companion;", "", "()V", "db", "Lcom/moviesearch/datasource/database/FilmDb;", "getDb", "()Lcom/moviesearch/datasource/database/FilmDb;", "setDb", "(Lcom/moviesearch/datasource/database/FilmDb;)V", "getDatabase", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.moviesearch.datasource.database.FilmDb getDb() {
            return null;
        }
        
        public final void setDb(@org.jetbrains.annotations.Nullable()
        com.moviesearch.datasource.database.FilmDb p0) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.moviesearch.datasource.database.FilmDb getDatabase() {
            return null;
        }
    }
}