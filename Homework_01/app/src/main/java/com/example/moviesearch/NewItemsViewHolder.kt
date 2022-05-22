package com.example.moviesearch

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


class NewItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val imageView: ImageView = itemView.findViewById(R.id.image)
    private val nameTV: TextView = itemView.findViewById(R.id.textName)
    private val descriptorTV: TextView = itemView.findViewById(R.id.text_short_description)
    private val detailBtn: Button = itemView.findViewById(R.id.button_detail)
    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(item: NewItem, listener: NewItemsAdapter.DetailClickListener){
        nameTV.text = item.name
        descriptorTV.text = item.description
        val cont = imageView.context
        var back_color = cont.resources.getColor(R.color.white, cont.theme)
        var font_color = cont.resources.getColor(R.color.black, cont.theme)
        if(item.Selected)
           {back_color = cont.resources.getColor(R.color.back_sel_item, cont.theme)
            font_color = cont.resources.getColor(R.color.font_sel_item, cont.theme)}
        itemView.setBackgroundColor(back_color)
        nameTV.setTextColor(font_color)

        val resId: Int = cont.resources.getIdentifier(item.pictures, "drawable", cont.packageName)
        imageView.setImageResource(resId)
        detailBtn.setOnClickListener{(listener.onDetailClick(item, absoluteAdapterPosition))}
    }
}
