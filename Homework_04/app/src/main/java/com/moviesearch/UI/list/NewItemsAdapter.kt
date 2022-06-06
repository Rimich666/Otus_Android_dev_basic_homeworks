package com.moviesearch.UI.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.moviesearch.UI.NewItem
import com.moviesearch.R

class NewItemsAdapter (
    private  val items: List<NewItem>,
    private val listener: DetailClickListener
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return NewItemsViewHolder(inflater.inflate(R.layout.new_item, parent, false))
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder)
            {
                is NewItemsViewHolder ->
                {
                    holder.bind(items[position], listener)
                }
            }

        }

        override fun getItemCount(): Int = items.size

        interface DetailClickListener {
            fun onDetailClick(newsItem: NewItem, position: Int)
            fun onItemLongClick(newsItem: NewItem, position: Int):Boolean
            fun onHeartClick(newsItem: NewItem, position: Int)
        }
    }