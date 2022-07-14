package com.moviesearch.ui.deferred

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moviesearch.R
import com.moviesearch.databinding.FragmentDeferredFilmsBinding
import com.moviesearch.ui.NewItem
import com.moviesearch.ui.favourites.FavoriteItemsAdapter
import com.moviesearch.ui.favourites.FavouritesFragment
import com.moviesearch.viewmodel.MainViewModel

class DeferredFilmsFragment : Fragment() {
    private lateinit var binding: FragmentDeferredFilmsBinding
    private lateinit var mainModel: MainViewModel
    private lateinit var deferredFilms: MutableList<NewItem>
    lateinit var host: HostDefer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainModel.currFragment = "defr"
        deferredFilms = mainModel.deferredFilms.value!!
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

    private fun initRecycler(){
        binding.recyclerDeferredFilms.adapter = DeferredItemsAdapter(
            deferredFilms, object: DeferredItemsAdapter.DeferredClickListener
            {
                override fun onDeleteClick(item: NewItem, position: Int) {
                    TODO("Not yet implemented")
                }

                override fun onDeferClick(item: NewItem, position: Int) {
                    TODO("Not yet implemented")
                }
            /*override fun onHeartClick(item: NewItem, position: Int) {
                    host.dislike(item, position)
                }*/
            }
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = DeferredFilmsFragment()
    }

    interface HostDefer{

    }
}