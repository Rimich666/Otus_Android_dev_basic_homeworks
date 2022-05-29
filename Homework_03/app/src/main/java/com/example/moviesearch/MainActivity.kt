package com.example.moviesearch

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), ListMovieFragment.Host {

    private var favourites: ArrayList<Int> = arrayListOf()
    private lateinit var items: Items
    private var selectedPosition: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Create Activity", "-----------------------------")
        Log.d("Create Activity", "Сразу после супера")
        Log.d("Create Activity", "savedInstanceState : {${savedInstanceState.toString()}}")
        if (savedInstanceState!=null){ restoreValues(savedInstanceState) }
        else {items = Items(resources.getString(R.string.movies))}

        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setOnItemSelectedListener { itemSelected(it) }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListMovieFragment.newInstance(items.bundle, favourites))
            .commit()
    }

    private fun restoreValues(saved: Bundle){
        selectedPosition = saved.getInt("selected")
        items = Items(saved.getBundle("items") as Bundle)
        favourites = saved.getIntegerArrayList("favorites") as ArrayList<Int>
        if (selectedPosition > -1) {items[selectedPosition].Selected = true}
        favourites.forEach { items[it].liked = true }

    }

    private fun itemSelected (mI: MenuItem): Boolean
    {
        when (mI.itemId) {
            R.id.navigation_list -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ListMovieFragment.newInstance(items.bundle, favourites))
                    .commit()
                Log.d("Ботомное меню", "Домик")
                return true
            }
            R.id.navigation_favorites -> {
                Log.d("supportFragmentManager", "Фавориты: ${favourites.toString()}")
                supportFragmentManager.setFragmentResultListener("del_fav", this){
                    _, result ->
                    val pos = result.getInt("pos")
                    items[favourites[pos]].liked = false
                    favourites.removeAt(pos)
                }
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FavouritesFragment.newInstance(items.select(favourites).bundle, favourites))
                        .commit()

                Log.d("Ботомное меню", "Панель инструментов")
                return true
            }
            R.id.navigation_notifications -> {
                Log.d("Ботомное меню", "Сообщения")
                return true
            }
        }
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selected", selectedPosition)
        outState.putBundle("items", items.bundle)
        outState.putIntegerArrayList("favorites", favourites )
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Предупреждение")
            .setMessage("Вы действительно хотите выйти из приложения?")
            .setPositiveButton("Да") { dialog, which ->
                super.onBackPressed()
            }
            .setNegativeButton("Нет") { dialog, which ->
                Toast.makeText(applicationContext, "Ну ладно", Toast.LENGTH_LONG).show()
            }
            .show()

    }

    override fun showDetail(position:Int, item: Bundle){
        Log.d("showDetail", "Слава тебе ... яйца!!! ЗАРАБОТАЛО, мля")
        if (selectedPosition > -1){ items[selectedPosition].Selected = false }
        selectedPosition = position
        items[position].Selected = true
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailFragment.newInstance(item))
            .commit()
    }

    override fun likedItem(position: Int, liked: Boolean){
        items[position].liked = liked
        if (liked){favourites.add(position)}
        else{ favourites.remove(position) }
    }
}
