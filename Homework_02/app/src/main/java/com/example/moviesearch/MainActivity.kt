package com.example.moviesearch

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.JsonReader
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import java.io.StringReader

class MainActivity : AppCompatActivity() {
    private val recyclerView by lazy {findViewById<RecyclerView>(R.id.recycler_view)}
    //private var items: List<NewItem> = mutableListOf()
    private lateinit var items: Items
    private var favorites: Items = Items()
    private var selectedPosition: Int = -1
    val detailLauncher = registerForActivityResult(DetailActivityContract()) { result ->
        Log.d("poin 8 with *","Результат закрытия второго экрана: $result ")
    }
    val favoritesLauncher = registerForActivityResult(FavoritesActivityContract()) { result ->
        Log.d("","Результат закрытия списка избранного: $result ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.action_favorites){
            //val bndl:Bundle = bundleOf("msg" to "Список избранного")
            favoritesLauncher.launch(favorites.bundle)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selected", selectedPosition)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        selectedPosition = savedInstanceState.getInt("selected")
        Log.d("tracing","А это рестор инстанс стейт, позишн = $selectedPosition")
        if (selectedPosition > -1)
        {
            items.list[selectedPosition].Selected = true
            recyclerView.adapter?.notifyItemChanged(selectedPosition)
        }

    }


    private fun initRecycler(){
        val layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) GridLayoutManager(this, 2) else LinearLayoutManager(this)
        items = Items(resources.getString(R.string.movies))
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = NewItemsAdapter(items.list, object:NewItemsAdapter.DetailClickListener
        {
            override fun onDetailClick(newsItem: NewItem, position: Int) {
                newsItem.Selected = true
                if (selectedPosition > -1)
                {
                    items.list[selectedPosition].Selected = false
                    recyclerView.adapter?.notifyItemChanged(selectedPosition)
                }
                selectedPosition = position

                recyclerView.adapter?.notifyItemChanged(position)

                detailLauncher.launch(newsItem.bundle)
            }

            override fun onItemLongClick(newsItem: NewItem, position: Int):Boolean{
                favorites.add(newsItem)
                Log.d("MainActivity", "Long click on item")
                return true
            }

            override fun onHeartClick(newsItem: NewItem, position: Int) {
                Log.d("onHeartClick", "before click list.size = ${favorites.list.size}")
                favorites.add(newsItem)
                Log.d("onHeartClick", "after click list.size = ${favorites.list.size}")
                Log.d("onHeartClick", "Список избранного: ${favorites.toString()}")
                Log.d("MainActivity", "Click on heart")
            }

        } )
    }
}
