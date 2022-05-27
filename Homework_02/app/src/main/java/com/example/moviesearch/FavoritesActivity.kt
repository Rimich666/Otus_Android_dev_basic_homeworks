package com.example.moviesearch

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesActivity : AppCompatActivity() {
    private val recyclerView by lazy {findViewById<RecyclerView>(R.id.recycler_favor)}
    private lateinit var items : Items
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        initRecycler()
    }

    override fun onBackPressed() {
        val result = Intent().putExtra("msg", "Возврат из списка избранного")
        setResult(RESULT_OK, result)
        super.onBackPressed()
    }

    private fun initRecycler(){
        val layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) GridLayoutManager(this, 2) else LinearLayoutManager(this)
        Log.d("FavoritesActivity",intent.extras.toString())


        items = Items(intent.extras as Bundle)
        Log.d("FavoritesActivity","Список избранного: ${items.toString()}")
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = FavoriteItemsAdapter(items.list, object:FavoriteItemsAdapter.FavoritesClickListener
        {
            override fun onHeartClick(item: NewItem, position: Int) {

                recyclerView.adapter?.notifyItemChanged(position)
            }
        } )
    }
}