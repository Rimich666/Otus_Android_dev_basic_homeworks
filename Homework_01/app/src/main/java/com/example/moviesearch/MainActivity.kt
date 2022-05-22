package com.example.moviesearch

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.JsonReader
import android.util.Log
import android.widget.Toast
import java.io.StringReader

class MainActivity : AppCompatActivity() {
    private val recyclerView by lazy {findViewById<RecyclerView>(R.id.recycler_view)}
    private var items: List<NewItem> = mutableListOf()
    private var selectedPosition: Int = -1
    val detailLauncher = registerForActivityResult(DetailActivityContract()) { result ->
        Log.d("poin 8 with *","Результат закрытия второго экрана: $result ")
    }

    private fun fillList():List<NewItem>{
        val list = mutableListOf<NewItem>()
        val map = mutableMapOf<String, String>()
        val reader = JsonReader(StringReader(resources.getString(R.string.movies)))
        reader.beginArray()
        while (reader.hasNext()) {
            reader.beginObject()
            while (reader.hasNext()) map[reader.nextName()] = reader.nextString()
            reader.endObject()
            list.add(
                NewItem(
                    map["name"] as String,
                    map["description"] as String,
                    map["pictures"] as String,
                    false
                )
            )
        }
        reader.endArray()
        return list
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
    }


   override fun onSaveInstanceState(outState: Bundle) {
       super.onSaveInstanceState(outState)
       outState.putInt("selected", selectedPosition)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        if (selectedPosition > -1)
        {
            items[selectedPosition].Selected = true
            recyclerView.adapter?.notifyItemChanged(selectedPosition)
        }
        Log.d("tracing","А это рестор инстанс стейт, позишн = $selectedPosition")
    }


    private fun initRecycler(){
        val layoutManager =
            LinearLayoutManager(this)
        items = fillList()
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = NewItemsAdapter(items, object:NewItemsAdapter.DetailClickListener
        {
            override fun onDetailClick(newsItem: NewItem, position: Int) {
                newsItem.Selected = true
                if (selectedPosition > -1)
                {
                    items[selectedPosition].Selected = false
                    recyclerView.adapter?.notifyItemChanged(selectedPosition)
                }
                selectedPosition = position

                recyclerView.adapter?.notifyItemChanged(position)

                detailLauncher.launch(newsItem.bundle)
            }
        } )
    }
}
