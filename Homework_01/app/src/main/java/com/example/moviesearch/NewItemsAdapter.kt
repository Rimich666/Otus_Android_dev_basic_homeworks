package com.example.moviesearch

import java.util.*

class NewItemsAdapter (private  val items: List<NewItem>, private val listener: NewClickListener){
    interface NewClickListener {
        fun onNewsClick(newsItem: NewItem, position: Int)
        fun onFavoriteClick(newsItem: NewItem, position: Int)
    }
}