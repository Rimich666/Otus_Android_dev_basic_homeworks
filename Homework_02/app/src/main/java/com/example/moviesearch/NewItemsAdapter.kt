package com.example.moviesearch

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

private const val HEADER_VIEW_TYPE = 0
private const val TAG = "Tracing"

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
            //Log.d(TAG, "onBindViewHolder : $position")
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