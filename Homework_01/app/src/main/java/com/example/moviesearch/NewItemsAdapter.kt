package com.example.moviesearch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.*

private const val HEADER_VIEW_TYPE = 0

class NewItemsAdapter (
    private  val items: List<NewItem>,
    private val listener: DetailClickListener
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return NewItemsViewHolder(inflater.inflate(R.layout.new_item, parent, false))
        }

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

        }
    }