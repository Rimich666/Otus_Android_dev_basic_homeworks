package com.moviesearch.UI.favourites;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0019B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0010H\u0016J\u0018\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0016R\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/moviesearch/UI/favourites/FavoriteItemsAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "items", "", "Lcom/moviesearch/UI/NewItem;", "listener", "Lcom/moviesearch/UI/favourites/FavoriteItemsAdapter$FavoritesClickListener;", "(Ljava/util/List;Lcom/moviesearch/UI/favourites/FavoriteItemsAdapter$FavoritesClickListener;)V", "binding", "Lcom/moviesearch/databinding/FavItemBinding;", "getBinding", "()Lcom/moviesearch/databinding/FavItemBinding;", "setBinding", "(Lcom/moviesearch/databinding/FavItemBinding;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "FavoritesClickListener", "app_debug"})
public final class FavoriteItemsAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    private final java.util.List<com.moviesearch.UI.NewItem> items = null;
    private final com.moviesearch.UI.favourites.FavoriteItemsAdapter.FavoritesClickListener listener = null;
    public com.moviesearch.databinding.FavItemBinding binding;
    
    public FavoriteItemsAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.moviesearch.UI.NewItem> items, @org.jetbrains.annotations.NotNull()
    com.moviesearch.UI.favourites.FavoriteItemsAdapter.FavoritesClickListener listener) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.moviesearch.databinding.FavItemBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull()
    com.moviesearch.databinding.FavItemBinding p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&\u00a8\u0006\b"}, d2 = {"Lcom/moviesearch/UI/favourites/FavoriteItemsAdapter$FavoritesClickListener;", "", "onHeartClick", "", "item", "Lcom/moviesearch/UI/NewItem;", "position", "", "app_debug"})
    public static abstract interface FavoritesClickListener {
        
        public abstract void onHeartClick(@org.jetbrains.annotations.NotNull()
        com.moviesearch.UI.NewItem item, int position);
    }
}