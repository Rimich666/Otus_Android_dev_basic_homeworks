package com.moviesearch.ui.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.moviesearch.ui.NewItem
import com.moviesearch.R
import com.moviesearch.databinding.NewItemBinding


class NewItemsViewHolder(private val binding: NewItemBinding) : RecyclerView.ViewHolder(binding.root){
    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(item: NewItem, listener: NewItemsAdapter.DetailClickListener){
        val cont = binding.root.context
        val fontColor =
            if(item.Selected)
                cont.resources.getColor(R.color.font_sel_item, cont.theme)
            else
                cont.resources.getColor(R.color.black, cont.theme)
        val iconFav =
            if(item.liked)
                (cont.resources.getIdentifier("favourite_color","drawable",cont.packageName))
            else (cont.resources.getIdentifier("favourite_cont","drawable",cont.packageName))


        binding.detailImage.setImageResource(cont.resources.getIdentifier("details", "drawable", cont.packageName))
        binding.heartImage.setImageResource(iconFav)
        binding.textShortDescription.text = item.description
        binding.textName.setTextColor(fontColor)
        binding.textName.text = item.name

        Glide.with(cont)
            .load(item.pictures)
            .into(binding.image)

        binding.buttonDetail.setOnClickListener { (listener.onDetailClick(item, absoluteAdapterPosition)) }
        itemView.setOnLongClickListener{(listener.onItemLongClick(item, absoluteAdapterPosition))}
        binding.heartImage.setOnClickListener{(listener.onHeartClick(item, absoluteAdapterPosition))}
    }
}
