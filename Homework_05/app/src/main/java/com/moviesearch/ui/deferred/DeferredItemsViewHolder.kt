package com.moviesearch.ui.deferred

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moviesearch.databinding.DeferItemBinding
import com.moviesearch.ui.NewItem

class DeferredItemsViewHolder(private val binding: DeferItemBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NewItem, listener: DeferredItemsAdapter.DeferredClickListener){
        binding.textNameDefer.text = item.name
        binding.textShortDescriptionDefer.text = item.description
        binding.idWork.text = item.idWork
        binding.textDateTime.text = item.deferDateTime
        val cont = binding.imageDefer.context
        binding.deferredImage.setOnClickListener{
            listener.onDeferClick(item, absoluteAdapterPosition)
        }
        binding.deleteImage.setOnClickListener{
            listener.onDeleteClick(item, absoluteAdapterPosition)
        }
        Glide.with(cont)
            .load(item.pictures)
            .into(binding.imageDefer)
    }
}