package com.example.moviesearch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.JsonReader
import android.util.Log
import android.widget.Toast
import java.io.StringReader

class MainActivity : AppCompatActivity() {
    private val recyclerView by lazy {findViewById<RecyclerView>(R.id.recycler_view)}
    private var items: List<NewItem> = mutableListOf()

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

    private fun initRecycler(){
        val layoutManager = LinearLayoutManager(this)
        items = fillList()
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = NewItemsAdapter(items, object:NewItemsAdapter.DetailClickListener
        {
            override fun onDetailClick(newsItem: NewItem, position: Int) {
                println("item: $newsItem       position: $position")
                Log.d("TAG", "Кнопочка нажата")
                newsItem.Selected = true
                recyclerView.adapter?.notifyItemChanged(position)
                Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show()
            }
        } )
    }
}