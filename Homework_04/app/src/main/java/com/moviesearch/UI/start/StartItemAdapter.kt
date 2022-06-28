package com.moviesearch.UI.start

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.moviesearch.R
import com.moviesearch.UI.favourites.FavoriteItemsViewHolder
import com.moviesearch.databinding.StartItemBinding
import com.moviesearch.trace

class StartItemAdapter (
    private val items: MutableList<StartItem>
        ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var binding: StartItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.start_item, parent, false)
        return StartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("start", "${trace()} Адаптер position: $position")
        when (holder)
        {
            is StartItemViewHolder ->
            {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int = items.size
}