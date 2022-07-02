package com.moviesearch.UI.list

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.moviesearch.UI.NewItem
import com.moviesearch.R
import com.moviesearch.databinding.NewItemBinding
import com.moviesearch.trace


class NewItemsViewHolder(private val binding: NewItemBinding) : RecyclerView.ViewHolder(binding.root){

    private val nameTV: TextView = itemView.findViewById(R.id.textName)
    private val detailBtn: Button = itemView.findViewById(R.id.button_detail)
    private val heartIm: ImageView = itemView.findViewById(R.id.heart_image)

    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(item: NewItem, listener: NewItemsAdapter.DetailClickListener){
        binding.textName.text = item.name
        binding.textShortDescription.text = item.description
        val cont = binding.root.context
        var back_color = cont.resources.getColor(R.color.white, cont.theme)
        var font_color = cont.resources.getColor(R.color.black, cont.theme)
        if(item.Selected)
           {back_color = cont.resources.getColor(R.color.back_sel_item, cont.theme)
            font_color = cont.resources.getColor(R.color.font_sel_item, cont.theme)}
        var iconFav = (cont.resources.getIdentifier("favourite_cont","drawable",cont.packageName))
        if(item.liked){iconFav = (cont.resources.getIdentifier("favourite_color","drawable",cont.packageName))}
        heartIm.setImageResource(iconFav)
        itemView.setBackgroundColor(back_color)
        binding.textName.setTextColor(font_color)
        Glide.with(cont)
            .load(item.pictures)
            .into(binding.image)

        detailBtn.setOnClickListener{(listener.onDetailClick(item, absoluteAdapterPosition))}
        itemView.setOnLongClickListener{(listener.onItemLongClick(item, absoluteAdapterPosition))}
        heartIm.setOnClickListener{(listener.onHeartClick(item, absoluteAdapterPosition))}
    }
}
