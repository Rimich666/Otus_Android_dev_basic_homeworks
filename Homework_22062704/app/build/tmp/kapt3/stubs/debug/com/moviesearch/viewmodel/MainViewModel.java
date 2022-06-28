package com.moviesearch.viewmodel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b)\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001fB\u0017\u0012\u0010\u0010\u0002\u001a\f\u0012\u0004\u0012\u00020\u0004\u0012\u0002\b\u00030\u0003\u00a2\u0006\u0002\u0010\u0005J\u0011\u0010N\u001a\u00020OH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010PJ\u0019\u0010Q\u001a\u00020O2\u0006\u0010R\u001a\u00020\u0013H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010SJ!\u0010T\u001a\u00020O2\u0006\u0010U\u001a\u00020 2\u0006\u0010V\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010WJ\u0011\u0010X\u001a\u00020OH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010PJ4\u0010Y\u001a\u00020O2!\u0010Z\u001a\u001d\u0012\u0013\u0012\u00110%\u00a2\u0006\f\b\\\u0012\b\b]\u0012\u0004\b\b(^\u0012\u0004\u0012\u00020O0[H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010_J!\u0010`\u001a\u00020O2\u0006\u0010a\u001a\u00020 2\u0006\u0010b\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010WJ\u0016\u0010c\u001a\u00020O2\f\u0010d\u001a\b\u0012\u0004\u0012\u00020e0\u001fH\u0002R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R \u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\n\"\u0004\b\u001a\u0010\fR\u001a\u0010\u001b\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u000f\"\u0004\b\u001d\u0010\u0011R&\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\n\"\u0004\b\"\u0010\fR\u000e\u0010#\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010$\u001a\u00020%X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\'\"\u0004\b(\u0010)R \u0010*\u001a\b\u0012\u0004\u0012\u00020 0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\n\"\u0004\b,\u0010\fR \u0010-\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\n\"\u0004\b/\u0010\fR \u00100\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\n\"\u0004\b2\u0010\fR&\u00103\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\n\"\u0004\b5\u0010\fR\u000e\u00106\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u00107\u001a\u00020%X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u0010\'\"\u0004\b9\u0010)R \u0010:\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u0010\n\"\u0004\b<\u0010\fR\u001c\u0010=\u001a\u0004\u0018\u00010\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010\u0015\"\u0004\b?\u0010\u0017R\u001c\u0010@\u001a\u0004\u0018\u00010\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bA\u0010\u0015\"\u0004\bB\u0010\u0017R \u0010C\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010\n\"\u0004\bE\u0010\fR \u0010F\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bG\u0010\n\"\u0004\bH\u0010\fR\u001a\u0010I\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010M\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006g"}, d2 = {"Lcom/moviesearch/viewmodel/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "settings", "", "", "(Ljava/util/Map;)V", "changeItem", "Landroidx/lifecycle/MutableLiveData;", "", "getChangeItem", "()Landroidx/lifecycle/MutableLiveData;", "setChangeItem", "(Landroidx/lifecycle/MutableLiveData;)V", "currFragment", "getCurrFragment", "()Ljava/lang/String;", "setCurrFragment", "(Ljava/lang/String;)V", "currP", "Lcom/moviesearch/viewmodel/MainViewModel$Page;", "getCurrP", "()Lcom/moviesearch/viewmodel/MainViewModel$Page;", "setCurrP", "(Lcom/moviesearch/viewmodel/MainViewModel$Page;)V", "deletedItem", "getDeletedItem", "setDeletedItem", "detailsText", "getDetailsText", "setDetailsText", "favourites", "", "Lcom/moviesearch/UI/NewItem;", "getFavourites", "setFavourites", "firstPage", "firstStart", "", "getFirstStart", "()Z", "setFirstStart", "(Z)V", "forCancel", "getForCancel", "setForCancel", "insertFavourite", "getInsertFavourite", "setInsertFavourite", "insertItem", "getInsertItem", "setInsertItem", "items", "getItems", "setItems", "lastPage", "loading", "getLoading", "setLoading", "maxProgress", "getMaxProgress", "setMaxProgress", "nextP", "getNextP", "setNextP", "prevP", "getPrevP", "setPrevP", "progress", "getProgress", "setProgress", "removeFavourite", "getRemoveFavourite", "setRemoveFavourite", "selectedPosition", "getSelectedPosition", "()I", "setSelectedPosition", "(I)V", "cancelLiked", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deletePage", "page", "(Lcom/moviesearch/viewmodel/MainViewModel$Page;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dislike", "itemFav", "posFav", "(Lcom/moviesearch/UI/NewItem;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNext", "initData", "prog", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "complete", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeOrAddFavour", "item", "pos", "setFavour", "fav", "Lcom/moviesearch/datasource/database/Favourite;", "Page", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currFragment;
    private boolean firstStart;
    private int selectedPosition;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String detailsText = "";
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> progress;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> maxProgress;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.util.List<com.moviesearch.UI.NewItem>> items;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.util.List<com.moviesearch.UI.NewItem>> favourites;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> changeItem;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> insertFavourite;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> removeFavourite;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<com.moviesearch.UI.NewItem> forCancel;
    private boolean loading = false;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> insertItem;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> deletedItem;
    @org.jetbrains.annotations.Nullable()
    private com.moviesearch.viewmodel.MainViewModel.Page prevP;
    @org.jetbrains.annotations.Nullable()
    private com.moviesearch.viewmodel.MainViewModel.Page currP;
    @org.jetbrains.annotations.Nullable()
    private com.moviesearch.viewmodel.MainViewModel.Page nextP;
    private int lastPage = 1;
    private int firstPage = 1;
    
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ?> settings) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrFragment() {
        return null;
    }
    
    public final void setCurrFragment(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final boolean getFirstStart() {
        return false;
    }
    
    public final void setFirstStart(boolean p0) {
    }
    
    public final int getSelectedPosition() {
        return 0;
    }
    
    public final void setSelectedPosition(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDetailsText() {
        return null;
    }
    
    public final void setDetailsText(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getProgress() {
        return null;
    }
    
    public final void setProgress(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getMaxProgress() {
        return null;
    }
    
    public final void setMaxProgress(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.util.List<com.moviesearch.UI.NewItem>> getItems() {
        return null;
    }
    
    public final void setItems(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.util.List<com.moviesearch.UI.NewItem>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.util.List<com.moviesearch.UI.NewItem>> getFavourites() {
        return null;
    }
    
    public final void setFavourites(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.util.List<com.moviesearch.UI.NewItem>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getChangeItem() {
        return null;
    }
    
    public final void setChangeItem(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getInsertFavourite() {
        return null;
    }
    
    public final void setInsertFavourite(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getRemoveFavourite() {
        return null;
    }
    
    public final void setRemoveFavourite(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.moviesearch.UI.NewItem> getForCancel() {
        return null;
    }
    
    public final void setForCancel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<com.moviesearch.UI.NewItem> p0) {
    }
    
    public final boolean getLoading() {
        return false;
    }
    
    public final void setLoading(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getInsertItem() {
        return null;
    }
    
    public final void setInsertItem(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getDeletedItem() {
        return null;
    }
    
    public final void setDeletedItem(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.moviesearch.viewmodel.MainViewModel.Page getPrevP() {
        return null;
    }
    
    public final void setPrevP(@org.jetbrains.annotations.Nullable()
    com.moviesearch.viewmodel.MainViewModel.Page p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.moviesearch.viewmodel.MainViewModel.Page getCurrP() {
        return null;
    }
    
    public final void setCurrP(@org.jetbrains.annotations.Nullable()
    com.moviesearch.viewmodel.MainViewModel.Page p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.moviesearch.viewmodel.MainViewModel.Page getNextP() {
        return null;
    }
    
    public final void setNextP(@org.jetbrains.annotations.Nullable()
    com.moviesearch.viewmodel.MainViewModel.Page p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getNext(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    private final java.lang.Object deletePage(com.moviesearch.viewmodel.MainViewModel.Page page, kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object initData(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> prog, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    private final void setFavour(java.util.List<com.moviesearch.datasource.database.Favourite> fav) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object cancelLiked(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object dislike(@org.jetbrains.annotations.NotNull()
    com.moviesearch.UI.NewItem itemFav, int posFav, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeOrAddFavour(@org.jetbrains.annotations.NotNull()
    com.moviesearch.UI.NewItem item, int pos, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\f\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\t\u00a8\u0006\u000f"}, d2 = {"Lcom/moviesearch/viewmodel/MainViewModel$Page;", "", "first", "", "last", "page", "size", "(IIII)V", "getFirst", "()I", "setFirst", "(I)V", "getLast", "setLast", "getSize", "app_debug"})
    public static final class Page {
        private int first;
        private int last;
        private final int size = 0;
        
        public Page(int first, int last, int page, int size) {
            super();
        }
        
        public final int getFirst() {
            return 0;
        }
        
        public final void setFirst(int p0) {
        }
        
        public final int getLast() {
            return 0;
        }
        
        public final void setLast(int p0) {
        }
        
        public final int getSize() {
            return 0;
        }
    }
}