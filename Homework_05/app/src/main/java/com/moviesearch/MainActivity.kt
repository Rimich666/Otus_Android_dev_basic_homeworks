//aasfasf safasf        moviesearch-dfb58
package com.moviesearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider


import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.moviesearch.ui.NewItem
import com.moviesearch.ui.start.StartFragment
import com.moviesearch.ui.detail.DetailFragment
import com.moviesearch.ui.favourites.FavouritesFragment
import com.moviesearch.ui.list.ListMovieFragment
import com.moviesearch.databinding.ActivityMainBinding
import com.moviesearch.repository.Repository
import com.moviesearch.viewmodel.MainViewModel
import com.moviesearch.viewmodel.MainViewModelFactory
import kotlinx.coroutines.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(),
    ListMovieFragment.HostList, FavouritesFragment.Host {

    private lateinit var items: MutableList<NewItem>
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: MainViewModelFactory
    //private val repository = $Repository
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
        viewModel.forCancel.observe(this){ showCancel() }
        viewModel.responseComplete.observe(this){
            if (!viewModel.responseComplete.value!!){ showRepeat() }

        }
        viewModel.atAll.observe(this){ if(it) inflateFragment["list"]?.let { it() }}
        Log.d("MainActivity.OnCreate", "${viewModel.firstStart}")
        if (viewModel.firstStart){
            scope.launch {
                //viewModel.initData{complete -> if(complete) inflateFragment["list"]?.let { it() }}
                viewModel.initData()
            }
            viewModel.firstStart = false
        }
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setOnItemSelectedListener { itemSelected(it) }
        inflateFragment[viewModel.currFragment]?.let { it() }
    }

    override fun showDetail(position:Int){
        if (viewModel.selectedPosition > -1){ viewModel.items.value!![viewModel.selectedPosition].Selected = false }
        viewModel.selectedPosition = position
        val item = viewModel.items.value!![position]
        item.Selected = true
        scope.launch {
            Repository.getDetails(item.idKp, tkDet)
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
            .replace(R.id.container, StartFragment.newInstance())
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
            .setPositiveButton("Да") { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton("Нет") { _, _ ->
                Toast.makeText(applicationContext, "Ну ладно", Toast.LENGTH_LONG).show()
            }
            .show()

    }

    override fun dislike(item: NewItem, pos: Int){
        scope.launch { viewModel.dislike(item, pos) }
    }

    override fun getNext(){
        scope.launch { viewModel.getNext() }
    }

    override fun getPrevious(){
        scope.launch { viewModel.getPrevious() }
    }

    override fun likedItem(item: NewItem, position: Int) {
        scope.launch { viewModel.removeOrAddFavour(item, position) }
    }

    private fun cancelLiked(){
        scope.launch { viewModel.cancelLiked() }
    }

    private fun showCancel(){
        val liked = viewModel.forCancel.value?.liked
        val map = mapOf(true to "добавили", false to "удалили")
        val zakus = Snackbar.make(
            findViewById(binding.container.id),
            "Вы успешно ${map[liked]} фильм: ${viewModel.forCancel.value?.name}",
            Snackbar.LENGTH_LONG
        )
        zakus.setAction("Отменить") { cancelLiked() }
        zakus.show()
    }

    private fun showRepeat(){
        val zakus = Snackbar.make(
            findViewById(binding.container.id),
            "Не все страницы были успешно загружены",
            Snackbar.LENGTH_LONG
        )
        zakus.setAction("Повторить?") { scope.launch { viewModel.initData() }}
        zakus.addCallback(object : Snackbar.Callback(){
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                when(event){
                    DISMISS_EVENT_TIMEOUT -> exitProcess(-1)
                }
            }
        })
        zakus.show()
    }
}

