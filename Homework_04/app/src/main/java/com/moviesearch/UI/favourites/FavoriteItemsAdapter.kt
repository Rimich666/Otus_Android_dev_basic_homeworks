package com.moviesearch.UI.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moviesearch.UI.NewItem
import com.moviesearch.R

class FavoriteItemsAdapter (
    private  val items: List<NewItem>,
    private val listener: FavoritesClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FavoriteItemsViewHolder(inflater.inflate(R.layout.fav_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder)
        {
            is FavoriteItemsViewHolder ->
            {
                holder.bind(items[position], listener)
            }
        }
    }

    override fun getItemCount(): Int = items.size


    interface FavoritesClickListener{
        fun onHeartClick(item: NewItem, position: Int)
    }
}
