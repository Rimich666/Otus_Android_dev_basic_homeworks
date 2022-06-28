package com.moviesearch.UI.start

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.moviesearch.R
import com.moviesearch.databinding.StartItemBinding

class StartItemAdapter (
    private val items: List<StartItem>
        ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var binding: StartItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.start_item, parent, false)
        return StartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = items.size
}