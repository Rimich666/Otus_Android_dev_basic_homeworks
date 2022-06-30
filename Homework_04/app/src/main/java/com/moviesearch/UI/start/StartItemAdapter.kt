package com.moviesearch.UI.start

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.moviesearch.R
import com.moviesearch.databinding.InitCashItemBinding
import com.moviesearch.databinding.RequestedItemBinding

class StartItemAdapter (
    private var items: MutableList<StartItem>
        ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var binding: ViewDataBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return when(viewType){
            R.layout.requested_item -> RequestedViewHolder(binding as RequestedItemBinding)
            R.layout.init_cash_item ->InitCashViewHolder(binding as InitCashItemBinding)
            else -> throw IllegalStateException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder)
        {
            is RequestedViewHolder -> holder.bind(item as StartItem.Requested)
            is InitCashViewHolder -> holder.bind(item as StartItem.InitCash)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]){
            is StartItem.InitCash -> R.layout.init_cash_item
            is StartItem.Requested -> R.layout.requested_item
        }

        //return super.getItemViewType(position)
    }

    override fun getItemCount(): Int = items.size
}