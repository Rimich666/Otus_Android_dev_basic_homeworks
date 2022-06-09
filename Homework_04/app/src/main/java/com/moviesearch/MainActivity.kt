package com.moviesearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider


import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.moviesearch.UI.detail.DetailFragment
import com.moviesearch.UI.favourites.FavouritesFragment
import com.moviesearch.UI.list.ListMovieFragment
import com.moviesearch.databinding.ActivityMainBinding
import com.moviesearch.repository.repository
import com.moviesearch.viewmodel.Items
import com.moviesearch.viewmodel.MainViewModel
import com.moviesearch.viewmodel.MainViewModelFactory


class MainActivity : AppCompatActivity(),
    ListMovieFragment.Host,
    FavouritesFragment.Host {

    private var favourites: ArrayList<Int> = arrayListOf()
    private lateinit var items: Items
    private var selectedPosition: Int = -1
    private lateinit var item: Bundle

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: MainViewModelFactory
    private val repository = repository()
    private var setings: Map<String,*> = mapOf(
        "firstStart" to true,
        "startFragment" to "list"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModelFactory = MainViewModelFactory(setings)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        Log.d("MainActivity.OnCreate", "${viewModel.firstStart}")
        if (viewModel.firstStart){
            repository.initData(prgrss)
            viewModel.firstStart = false
        }

        if (savedInstanceState!=null){ restoreValues(savedInstanceState) }
        else {items = Items(resources.getString(R.string.movies))}
        Log.d("MainActivity", "----------------------------OnCreate--------------------------")
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setOnItemSelectedListener { itemSelected(it) }
        Log.d("MainActivity.OnCreate", "текущий фрагмент: ${viewModel.currFragment}")
        mapFun[viewModel.currFragment]?.let { it() }
    }

    private fun progress(msg: String){
        Log.d("progress", "----------------о это прогресс $msg")
    }
    private val prgrss: (String) -> Unit = ::progress

    private fun restoreValues(saved: Bundle){
        selectedPosition = saved.getInt("selected")
        items = Items(saved.getBundle("items") as Bundle)
        favourites = saved.getIntegerArrayList("favorites") as ArrayList<Int>
        if (selectedPosition > -1) {items[selectedPosition].Selected = true}
        favourites.forEach { items[it].liked = true }

    }

   /* private val list_fragment ={supportFragmentManager.beginTransaction()
        .replace(R.id.container, ListMovieFragment.newInstance(items.bundle, favourites))
        .commit()}

    private val favourites_fragment_fun = {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FavouritesFragment.newInstance(items.select(favourites).bundle))
            .commit()
    }*/

    private val mapFun: MutableMap<String, ()->Any> = mutableMapOf(
        "list" to {supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ListMovieFragment.newInstance(items.bundle, favourites))
                    .commit()},
        "favr" to {supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FavouritesFragment.newInstance(items.select(favourites).bundle))
                    .commit()},
        "detl" to {supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DetailFragment.newInstance(this.item))
                    .commit()}

    )



    private fun itemSelected (mI: MenuItem): Boolean
    {
        when (mI.itemId) {
            R.id.navigation_list -> {
                mapFun["list"]?.let { it() }
                return true
            }
            R.id.navigation_favorites -> {
                mapFun["favr"]?.let { it() }
                return true
            }
            R.id.navigation_notifications -> {
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
        this.item = item
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailFragment.newInstance(this.item))
            .commit()
    }

    override fun likedItem(position: Int, liked: Boolean) {
        changeLiked(position, liked, "list")
    }

    override fun dislike(pos: Int) {
        changeLiked(favourites[pos], false, "favr")
    }

    private fun changeLiked(pos: Int, liked: Boolean, frg: String){
        Log.d("changeLiked", "Ну таг всё сказал: это чейнж лайкед")
        rem_add_fav(pos, liked)
        val map = mapOf<Boolean, String>(true to "добавили", false to "удалили")
        val zakus = Snackbar.make(
            findViewById(R.id.container),
            "Вы успешно ${map[liked]} фильм: ${items[pos].name}",
            Snackbar.LENGTH_LONG)
        zakus.setAction("Отменить", View.OnClickListener {
            rem_add_fav(pos, !liked)
            mapFun[frg]?.let { it() }})
        zakus.show()

    }

    private fun rem_add_fav(pos: Int, liked: Boolean){
        items[pos].liked = liked
        if (liked){favourites.add(pos)}
        else{ favourites.remove(pos)}
        Log.d("rem_add_fav", "Список избранного: ${items.select(favourites)}")
    }
}
