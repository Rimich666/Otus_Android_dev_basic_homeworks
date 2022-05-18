package com.example.moviesearch

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.security.AccessController.getContext
import kotlin.coroutines.coroutineContext


class NewItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val imageView: ImageView = itemView.findViewById(R.id.image)
    private val nameTV: TextView = itemView.findViewById(R.id.textName)
    private val descriptorTV: TextView = itemView.findViewById(R.id.text_short_description)
    private val detailBtn: Button = itemView.findViewById(R.id.button_detail)
    fun bind(item: NewItem, listener: NewItemsAdapter.NewClickListener){
        nameTV.text = item.name
        descriptorTV.text = item.description
        val resID = getContext().toString() //.getResources().getIdentifier(item.pictures, "drawable", "com.example.moviesearch")
        //val resID: Int = getResources().getIdentifier(item.pictures, "drawable", "com.example.moviesearch")
        //val resID = resources.getIdentifier(item.pictures, "drawable", packageName)
        imageView.setImageResource(resID)
    }
}
