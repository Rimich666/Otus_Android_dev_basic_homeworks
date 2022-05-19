package com.example.moviesearch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.JsonReader
import android.util.Log
import java.io.StringReader

class MainActivity : AppCompatActivity() {
    private val recyclerView by lazy {findViewById<RecyclerView>(R.id.recycler_view)}

    private val items = fun(): List<NewItem>
    {
        val list = mutableListOf<NewItem>()
        val reader: JsonReader = JsonReader(StringReader(resources.getString(R.string.movies)))
        var name: String = ""
        var value: String = ""
        reader.beginArray()
        while (reader.hasNext())
        {
            reader.beginObject()

            while (reader.hasNext())
            {
                name = reader.nextName()
                value = reader.nextString()
            }
            reader.endObject()

        }
        reader.endArray()
        return list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun initRecycler(){
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = NewItemsAdapter(items, object: )
    }
}