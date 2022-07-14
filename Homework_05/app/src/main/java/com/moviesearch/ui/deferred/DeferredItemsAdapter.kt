package com.moviesearch.ui.deferred

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.moviesearch.R
import com.moviesearch.databinding.DeferItemBinding
import com.moviesearch.ui.NewItem

class DeferredItemsAdapter(private val items: List<NewItem>,
                           private val listener: DeferredClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var binding: DeferItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.defer_item, parent, false)
        return DeferredItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder)
        {
            is DeferredItemsViewHolder ->
            {
                holder.bind(items[position], listener)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface DeferredClickListener{
        fun onDeleteClick(item: NewItem, position: Int)
        fun onDeferClick(item: NewItem, position: Int)
    }
}