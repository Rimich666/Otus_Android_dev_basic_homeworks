//aasfasf safasf        moviesearch-dfb58
package com.moviesearch

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.replace
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.google.android.datatransport.runtime.util.PriorityMapping.toInt
import com.google.android.gms.tasks.OnCompleteListener


import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.moviesearch.ui.NewItem
import com.moviesearch.ui.start.StartFragment
import com.moviesearch.ui.detail.DetailFragment
import com.moviesearch.ui.favourites.FavouritesFragment
import com.moviesearch.ui.list.ListMovieFragment
import com.moviesearch.databinding.ActivityMainBinding
import com.moviesearch.firebase.RemoteConfig
import com.moviesearch.repository.Repository
import com.moviesearch.ui.deferred.DeferredFilmsFragment
import com.moviesearch.viewmodel.MainViewModel
import com.moviesearch.viewmodel.MainViewModelFactory
import kotlinx.coroutines.*
import java.lang.RuntimeException
import kotlin.system.exitProcess

enum class Frags{LIST {
    override var list: MutableList<NewItem>? = null
    override var inst: (() -> Fragment) = { ListMovieFragment.newInstance() }
    override var inflater: () -> Any = {}
    override fun antonymDefr(): Frags = DEFER
}, FAVOR {
    override var list: MutableList<NewItem>? = null
    override var inst: () -> Fragment = { FavouritesFragment.newInstance() }
    override var inflater: () -> Any = {}
    override fun antonymDefr(): Frags = FAVOR
}, START {
    override var list: MutableList<NewItem>? = null
    override var inst: () -> Fragment = { StartFragment.newInstance() }
    override var inflater: () -> Any = {}
    override fun antonymDefr(): Frags = START
}, DEFER {
    override var list: MutableList<NewItem>? = null
    override var inst: () -> Fragment = { DeferredFilmsFragment.newInstance() }
    override var inflater: () -> Any = {}
    override fun antonymDefr(): Frags = LIST
}, DETLS {
    override var list: MutableList<NewItem>? = null
    override var inst: () -> Fragment = { DetailFragment.newInstance() }
    override var inflater: () -> Any = {}
    override fun antonymDefr(): Frags = DETLS
};
    abstract var list: MutableList<NewItem>?
    abstract var inflater: () -> Any
    abstract var inst: () -> Fragment
    abstract fun antonymDefr(): Frags
}

class MainActivity : AppCompatActivity(),
    ListMovieFragment.HostList, FavouritesFragment.Host, DeferredFilmsFragment.HostDefer {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: MainViewModelFactory
    private val scope = CoroutineScope(Dispatchers.Default)
    object Settings{
        var firstStart = true
        var startFragment = Frags.START
        var progress = 0
        var selectedPosition = -1
        lateinit var owner: LifecycleOwner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Settings.owner = this
        intent.extras?.keySet()?.forEach {
            Log.d(FTTAG, "${trace()} onCreate: $it")
        }

        val idKp = when (val idKPSS = intent.extras?.get("idKp")){
            is Int -> idKPSS
            is String -> idKPSS.toInt()
            else -> -1
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModelFactory = MainViewModelFactory(Settings)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        Frags.LIST.list = viewModel.items.value
        Frags.FAVOR.list = viewModel.favourites.value
        Frags.DEFER.list = viewModel.deferredFilms.value
        Frags.values().forEach { frg ->
            frg.inflater = {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, frg.inst() )
                    .commit()
             }
        }
        Frags.LIST.inflater = {supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListMovieFragment.newInstance())
            .commit()}
        setContentView(R.layout.activity_main)
        if (idKp == -1){
            viewModel.forCancel.observe(this){ showCancel() }
            viewModel.responseComplete.observe(this){
                if (!viewModel.responseComplete.value!!){ showRepeat() }
            }
            viewModel.atAll.observe(this){
                if(it) Frags.LIST.inflater()
            }

            if (viewModel.firstStart){
                scope.launch {
                    viewModel.initData()
                }
                viewModel.firstStart = false
            }

            scope.launch { viewModel.observationOfWorks() }

            val navView = findViewById<BottomNavigationView>(R.id.nav_view)
            navView.setOnItemSelectedListener { itemSelected(it) }
            viewModel.currFragment.inflater()
        }
        else{
            scope.launch {
                viewModel.getDetails(idKp, Frags.DETLS.inflater)
            }
        }
     }

    override fun showDetail(position:Int){
        //throw RuntimeException("Test crash")
        if (viewModel.selectedPosition > -1){ viewModel.items.value!![viewModel.selectedPosition].selected = false }
        viewModel.selectedPosition = position
        val item = viewModel.items.value!![position]
        item.selected = true
        scope.launch {
            viewModel.getDetails(item.idKp, Frags.DETLS.inflater)
        }
    }

    private fun itemSelected (mI: MenuItem): Boolean
    {
        when (mI.itemId) {
            R.id.navigation_list -> {
                Frags.LIST.inflater()
                return true
            }
            R.id.navigation_favorites -> {
                Frags.FAVOR.inflater()
                return true
            }
            R.id.navigation_deferred ->{
                Frags.DEFER.inflater()
                return true
            }
            R.id.navigation_notifications -> {
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(FTTAG, "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result

                    // Log and toast
                    val msg = getString(R.string.msg_token_fmt, token)
                    Log.d(FTTAG, msg)
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                })
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

    override fun defer(item: NewItem, position: Int, dateTime: String) {
        scope.launch { viewModel.addDeferred(item, position, dateTime) }
    }

    override fun undefer(item: NewItem, position: Int) {
        scope.launch { viewModel.cancelWork(item, position) }
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
