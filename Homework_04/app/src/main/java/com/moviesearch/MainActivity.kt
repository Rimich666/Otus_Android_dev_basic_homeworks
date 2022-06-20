package com.moviesearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider


import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.moviesearch.UI.NewItem
import com.moviesearch.UI.StartFragment
import com.moviesearch.UI.detail.DetailFragment
import com.moviesearch.UI.favourites.FavouritesFragment
import com.moviesearch.UI.list.ListMovieFragment
import com.moviesearch.databinding.ActivityMainBinding
import com.moviesearch.repository.Repository
import com.moviesearch.viewmodel.MainViewModel
import com.moviesearch.viewmodel.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(),
    ListMovieFragment.Host {

    private var favourites: ArrayList<Int> = arrayListOf()

    private lateinit var items: MutableList<NewItem>
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: MainViewModelFactory
    private val repository = Repository()
    private val scope = CoroutineScope(Dispatchers.Default)
    private var setings: Map<String,*> = mapOf(
        "firstStart" to true,
        "startFragment" to "start",
        "progress" to 0,
        "selectedPosition" to -1
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModelFactory = MainViewModelFactory(setings)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        items = viewModel.items.value!!

        Log.d("MainActivity.OnCreate", "${viewModel.firstStart}")
        if (viewModel.firstStart){
            scope.launch {
                viewModel.initData{complete -> if(complete) inflateFragment["list"]?.let { it() }}
            }
            viewModel.firstStart = false
        }
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setOnItemSelectedListener { itemSelected(it) }
        Log.d("MainActivity.OnCreate", "текущий фрагмент: ${viewModel.currFragment}")
        inflateFragment[viewModel.currFragment]?.let { it() }
    }

    override fun showDetail(position:Int){
        if (viewModel.selectedPosition > -1){ viewModel.items.value!![viewModel.selectedPosition].Selected = false }
        viewModel.selectedPosition = position
        val item = viewModel.items.value!![position]
        item.Selected = true
        scope.launch {
            repository.getDetails(item.idKp, tkDet)
        }
    }

    private fun takeDetails(msg: String){
        //Log.d("request", "${trace()} $msg")
        viewModel.detailsText = msg
        inflateFragment["detl"]?.let { it() }
    }
    private val tkDet: (msg: String) -> Unit = ::takeDetails

    private val inflateFragment: MutableMap<String, ()->Any> = mutableMapOf(
        "start" to {supportFragmentManager.beginTransaction()
            .replace(R.id.container, StartFragment.newInstance("1","2"))
            .commit()},
        "list" to {supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ListMovieFragment.newInstance())
                    .commit()},
        "favr" to {supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FavouritesFragment.newInstance())
                    .commit()},
        "detl" to {supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DetailFragment.newInstance())
                    .commit()}

    )

    private fun itemSelected (mI: MenuItem): Boolean
    {
        when (mI.itemId) {
            R.id.navigation_list -> {
                inflateFragment["list"]?.let { it() }
                return true
            }
            R.id.navigation_favorites -> {
                inflateFragment["favr"]?.let { it() }
                return true
            }
            R.id.navigation_notifications -> {
                return true
            }
        }
        return false
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

    override fun likedItem(position: Int, liked: Boolean, item: NewItem) {
        changeLiked(position, liked, item,"list")
    }

    private fun changeLiked(pos: Int, liked: Boolean, item: NewItem, frg: String){
        Log.d("changeLiked", "${trace()} Ну таг всё сказал: это чейнж лайкед")
        //removeOrAddFavour(pos, liked, item)
        scope.launch{ viewModel.removeOrAddFavour(pos, liked, item) }
        val map = mapOf<Boolean, String>(true to "добавили", false to "удалили")
        val zakus = Snackbar.make(
            findViewById(R.id.container),
            "Вы успешно ${map[liked]} фильм: ${items[pos].name}",
            Snackbar.LENGTH_LONG)
        zakus.setAction("Отменить", View.OnClickListener {
            removeOrAddFavour(pos, !liked)
            inflateFragment[frg]?.let { it() }})
        zakus.show()
    }

    /*private fun removeOrAddFavour(pos: Int, liked: Boolean, item: NewItem){


        items[pos].liked = liked
        if (liked){favourites.add(pos)}
        else{ favourites.remove(pos)}
    }*/
}
