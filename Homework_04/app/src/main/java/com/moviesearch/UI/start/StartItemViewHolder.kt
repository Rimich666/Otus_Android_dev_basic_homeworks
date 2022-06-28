package com.moviesearch.UI.start

import androidx.recyclerview.widget.RecyclerView
import com.moviesearch.databinding.StartItemBinding

class StartItemViewHolder(private val binding: StartItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StartItem){
        binding.textAction.text = item.action
        binding.textResult.text = item.result
    }
}