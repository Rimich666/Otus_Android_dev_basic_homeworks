package com.moviesearch;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u001f\u001a\u00020\u001aH\u0002J\u0018\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020\u001aH\u0016J\u0010\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020(H\u0002J\u0018\u0010)\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020#H\u0016J\b\u0010+\u001a\u00020\u001aH\u0016J\u0012\u0010,\u001a\u00020\u001a2\b\u0010-\u001a\u0004\u0018\u00010.H\u0014J\b\u0010/\u001a\u00020\u001aH\u0002J\u0010\u00100\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020#H\u0016J\u0010\u00101\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00020\tH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R \u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0013\u001a\f\u0012\u0004\u0012\u00020\t\u0012\u0002\b\u00030\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R)\u0010\u0015\u001a\u001d\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u001a0\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lcom/moviesearch/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/moviesearch/UI/list/ListMovieFragment$HostList;", "Lcom/moviesearch/UI/favourites/FavouritesFragment$Host;", "()V", "binding", "Lcom/moviesearch/databinding/ActivityMainBinding;", "inflateFragment", "", "", "Lkotlin/Function0;", "", "items", "", "Lcom/moviesearch/UI/NewItem;", "repository", "Lcom/moviesearch/repository/Repository;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "setings", "", "tkDet", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "msg", "", "viewModel", "Lcom/moviesearch/viewmodel/MainViewModel;", "viewModelFactory", "Lcom/moviesearch/viewmodel/MainViewModelFactory;", "cancelLiked", "dislike", "item", "pos", "", "getNext", "itemSelected", "", "mI", "Landroid/view/MenuItem;", "likedItem", "position", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "showCancel", "showDetail", "takeDetails", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity implements com.moviesearch.UI.list.ListMovieFragment.HostList, com.moviesearch.UI.favourites.FavouritesFragment.Host {
    private java.util.List<com.moviesearch.UI.NewItem> items;
    private com.moviesearch.viewmodel.MainViewModel viewModel;
    private com.moviesearch.databinding.ActivityMainBinding binding;
    private com.moviesearch.viewmodel.MainViewModelFactory viewModelFactory;
    private final com.moviesearch.repository.Repository repository = null;
    private final kotlinx.coroutines.CoroutineScope scope = null;
    private java.util.Map<java.lang.String, ?> setings;
    private final kotlin.jvm.functions.Function1<java.lang.String, kotlin.Unit> tkDet = null;
    private final java.util.Map<java.lang.String, kotlin.jvm.functions.Function0<java.lang.Object>> inflateFragment = null;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void showDetail(int position) {
    }
    
    private final void takeDetails(java.lang.String msg) {
    }
    
    private final boolean itemSelected(android.view.MenuItem mI) {
        return false;
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    @java.lang.Override()
    public void dislike(@org.jetbrains.annotations.NotNull()
    com.moviesearch.UI.NewItem item, int pos) {
    }
    
    @java.lang.Override()
    public void getNext() {
    }
    
    @java.lang.Override()
    public void likedItem(@org.jetbrains.annotations.NotNull()
    com.moviesearch.UI.NewItem item, int position) {
    }
    
    private final void cancelLiked() {
    }
    
    private final void showCancel() {
    }
}