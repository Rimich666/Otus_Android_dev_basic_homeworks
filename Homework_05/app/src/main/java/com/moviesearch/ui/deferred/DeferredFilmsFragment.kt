package com.moviesearch.ui.deferred

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moviesearch.Frags
import com.moviesearch.R
import com.moviesearch.databinding.FragmentDeferredFilmsBinding
import com.moviesearch.ui.NewItem
import com.moviesearch.ui.date_time_picker.DateTimeDialog
import com.moviesearch.ui.favourites.FavoriteItemsAdapter
import com.moviesearch.ui.favourites.FavouritesFragment
import com.moviesearch.ui.list.ListMovieFragment
import com.moviesearch.viewmodel.MainViewModel

class DeferredFilmsFragment : Fragment() {
    private lateinit var binding: FragmentDeferredFilmsBinding
    private lateinit var mainModel: MainViewModel
    private lateinit var deferredFilms: MutableList<NewItem>
    lateinit var host: HostDefer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainModel.currFragment = Frags.DEFER
        deferredFilms = mainModel.deferredFilms.value!!
        mainModel.changeDeferred.observe(this){
            binding.recyclerDeferredFilms.adapter?.notifyItemChanged(it)
        }
        mainModel.deletedDeferred.observe(this){
            binding.recyclerDeferredFilms.adapter?.notifyItemRemoved(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deferred_films, container, false)
        initRecycler()
        val view = binding.root
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        host = context as HostDefer
    }

    private fun initRecycler(){
        binding.recyclerDeferredFilms.adapter = DeferredItemsAdapter(
            deferredFilms, object: DeferredItemsAdapter.DeferredClickListener
            {
                override fun onDeleteClick(item: NewItem, position: Int) {
                    host.undefer(item, position)
                }

                override fun onDeferClick(item: NewItem, position: Int) {
                    val dialog = DateTimeDialog.newInstance()
                    parentFragmentManager.setFragmentResultListener("selectDate", this@DeferredFilmsFragment) {
                            _, bundle ->
                        if (bundle.getBoolean("ok")) host.defer(item, position, bundle.getString("dateTime")!!)
                    }
                    dialog.show(activity!!.supportFragmentManager, DateTimeDialog.TAG)
                }
            }
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = DeferredFilmsFragment()
    }

    interface HostDefer{
        fun defer(item: NewItem, position: Int, dateTime: String)
        fun undefer(item: NewItem, position: Int)
    }
}