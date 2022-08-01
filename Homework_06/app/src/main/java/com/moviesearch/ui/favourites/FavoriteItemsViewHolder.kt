package com.moviesearch.ui.favourites

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moviesearch.ui.NewItem
import com.moviesearch.databinding.FavItemBinding

class FavoriteItemsViewHolder(private val binding: FavItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NewItem, listener: FavoriteItemsAdapter.FavoritesClickListener){
        binding.textNameFav.text = item.name
        binding.textShortDescriptionFav.text = item.description
        val cont = binding.imageFav.context
        binding.heartImageFav.setOnClickListener{(listener.onHeartClick(item, absoluteAdapterPosition))}

        Glide.with(cont)
            .load(item.pictures)
            .into(binding.imageFav)
    }
}