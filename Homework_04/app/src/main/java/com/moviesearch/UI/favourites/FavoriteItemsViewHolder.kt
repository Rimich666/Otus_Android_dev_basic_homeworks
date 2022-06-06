package com.moviesearch.UI.favourites

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moviesearch.UI.NewItem
import com.moviesearch.R

class FavoriteItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.image_fav)
    private val nameTV: TextView = itemView.findViewById(R.id.textName_fav)
    private val descriptorTV: TextView = itemView.findViewById(R.id.text_short_description_fav)
    private val heartIm: ImageView = itemView.findViewById(R.id.heart_image_fav)
    fun bind(item: NewItem, listener: FavoriteItemsAdapter.FavoritesClickListener){
        nameTV.text = item.name
        descriptorTV.text = item.description
        val cont = imageView.context
        val resId: Int = cont.resources.getIdentifier(item.pictures, "drawable", cont.packageName)
        imageView.setImageResource(resId)
        heartIm.setOnClickListener{(listener.onHeartClick(item, absoluteAdapterPosition))}
    }
}