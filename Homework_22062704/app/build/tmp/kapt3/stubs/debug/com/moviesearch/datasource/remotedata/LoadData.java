package com.moviesearch.datasource.remotedata;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002JL\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\b21\u0010\u0014\u001a-\b\u0001\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJd\u0010\u001b\u001a\u00020\u00122\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\b0\u001d2\u0006\u0010\u001e\u001a\u00020\b2;\u0010\u0014\u001a7\b\u0001\u0012\u001d\u0012\u001b\u0012\u0004\u0012\u00020\u0004\u0012\u0002\b\u00030\u001f\u00a2\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 J\u0010\u0010!\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020#H\u0002J\u001a\u0010$\u001a\u00020\u00042\u0010\u0010%\u001a\f\u0012\u0004\u0012\u00020\u0004\u0012\u0002\b\u00030\u001fH\u0002JE\u0010&\u001a\u00020\b2\u0006\u0010\'\u001a\u00020\u00042\u001a\u0010(\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00060)2\u0006\u0010*\u001a\u00020\b2\u0006\u0010+\u001a\u00020,H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010-R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006."}, d2 = {"Lcom/moviesearch/datasource/remotedata/LoadData;", "", "()V", "URL", "", "itemMap", "", "limit", "", "okHttpClient", "Lokhttp3/OkHttpClient;", "pagesCount", "getPagesCount", "()I", "setPagesCount", "(I)V", "token", "getDetail", "", "id", "updateResults", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "msg", "Lkotlin/coroutines/Continuation;", "(ILkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadPages", "pages", "", "currPage", "", "(Ljava/util/List;ILkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readObject", "reader", "Landroid/util/JsonReader;", "request", "pars", "toBase", "json", "channel", "Lkotlinx/coroutines/channels/Channel;", "page", "needRes", "", "(Ljava/lang/String;Lkotlinx/coroutines/channels/Channel;IZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class LoadData {
    @org.jetbrains.annotations.NotNull()
    public static final com.moviesearch.datasource.remotedata.LoadData INSTANCE = null;
    private static okhttp3.OkHttpClient okHttpClient;
    private static final java.lang.String URL = "https://api.kinopoisk.dev/movie";
    private static final java.lang.String token = "MKRJKN4-Q0B463J-J85RBPK-ENWYABY";
    private static int limit = 50;
    private static int pagesCount = 0;
    private static final java.util.Map<java.lang.String, java.lang.Object> itemMap = null;
    
    private LoadData() {
        super();
    }
    
    public final int getPagesCount() {
        return 0;
    }
    
    public final void setPagesCount(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadPages(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> pages, int currPage, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.util.Map<java.lang.String, ?>, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> updateResults, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getDetail(int id, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> updateResults, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    private final java.lang.String request(java.util.Map<java.lang.String, ?> pars) {
        return null;
    }
    
    private final java.lang.Object toBase(java.lang.String json, kotlinx.coroutines.channels.Channel<java.util.Map<java.lang.String, java.lang.Object>> channel, int page, boolean needRes, kotlin.coroutines.Continuation<? super java.lang.Integer> continuation) {
        return null;
    }
    
    private final void readObject(android.util.JsonReader reader) {
    }
}