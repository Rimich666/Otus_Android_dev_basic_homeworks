package com.moviesearch.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.moviesearch.ui.NewItem
import com.moviesearch.R
import com.moviesearch.databinding.FavItemBinding

class FavoriteItemsAdapter (
    private  val items: List<NewItem>,
    private val listener: FavoritesClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    lateinit var binding: FavItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.fav_item, parent, false)
        return FavoriteItemsViewHolder(binding)
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
