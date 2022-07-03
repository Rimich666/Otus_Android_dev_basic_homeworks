package com.moviesearch.ui.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.moviesearch.ui.NewItem
import com.moviesearch.R
import com.moviesearch.databinding.NewItemBinding

class NewItemsAdapter (
    private val items: List<NewItem>,
    private val listener: DetailClickListener
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>()
    {
        lateinit var binding: NewItemBinding

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            binding = DataBindingUtil.inflate(inflater, R.layout.new_item, parent, false)
            return NewItemsViewHolder(binding)
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