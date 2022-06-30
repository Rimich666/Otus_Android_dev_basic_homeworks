package com.moviesearch.UI.start

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.moviesearch.databinding.InitCashItemBinding
import com.moviesearch.databinding.RequestedItemBinding

class RequestedViewHolder(private val binding: RequestedItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(item: StartItem.Requested){
        binding.textAction.text = item.requested.action
        if (item.requested.successful == null){
            binding.progressRequest.visibility = VISIBLE
            binding.imageSuccessful.visibility = INVISIBLE
        }
        else{
            binding.progressRequest.visibility = INVISIBLE
            binding.imageSuccessful.visibility = VISIBLE
        }
    }
}

class InitCashViewHolder(private val binding: InitCashItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(item: StartItem.InitCash){
        binding.textTitleInitCash.text =
            "${item.initCash.aText} ${item.initCash.progress} из ${item.initCash.max}"
        binding.progressInitCash.max = item.initCash.max
        binding.progressInitCash.progress = item.initCash.progress
    }
}