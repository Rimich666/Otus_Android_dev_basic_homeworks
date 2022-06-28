package com.moviesearch.repository;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0019\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J<\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2!\u0010\f\u001a\u001d\u0012\u0013\u0012\u00110\u000e\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00120\rH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015J\u0016\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0018\u0018\u00010\u00172\u0006\u0010\u0019\u001a\u00020\u000bJ>\u0010\u001a\u001a\u00020\u00122+\u0010\u001b\u001a\'\u0012\u001d\u0012\u001b\u0012\u0004\u0012\u00020\u000e\u0012\u0002\b\u00030\u001c\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00120\rH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001dJ\u0019\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J\u0006\u0010\u001f\u001a\u00020\u0012\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006 "}, d2 = {"Lcom/moviesearch/repository/Repository;", "", "()V", "dislike", "", "item", "Lcom/moviesearch/UI/NewItem;", "(Lcom/moviesearch/UI/NewItem;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDetails", "Lkotlinx/coroutines/Job;", "id", "", "takeDetails", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "msg", "", "(ILkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMovieList", "", "getNext", "", "Lcom/moviesearch/datasource/database/Film;", "lastPage", "initData", "progress", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "like", "loadMovieList", "app_debug"})
public final class Repository {
    
    public Repository() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.moviesearch.UI.NewItem> getMovieList() {
        return null;
    }
    
    public final void loadMovieList() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.moviesearch.datasource.database.Film> getNext(int lastPage) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object initData(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.Map<java.lang.String, ?>, kotlin.Unit> progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getDetails(int id, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> takeDetails, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.Job> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object dislike(@org.jetbrains.annotations.NotNull()
    com.moviesearch.UI.NewItem item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object like(@org.jetbrains.annotations.NotNull()
    com.moviesearch.UI.NewItem item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> continuation) {
        return null;
    }
}