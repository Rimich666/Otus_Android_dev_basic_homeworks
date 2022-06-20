package com.moviesearch.UI.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.moviesearch.UI.NewItem
import com.moviesearch.R
import com.moviesearch.databinding.FragmentFavouritesBinding
import com.moviesearch.viewmodel.MainViewModel

class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var mainModel: MainViewModel
    private lateinit var items: MutableList<NewItem>
    private lateinit var favourites: MutableList<NewItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainModel.currFragment = "favr"
        items = mainModel.items.value!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_favourites, container, false)
        initRecycler()
        return view
    }

    private fun initRecycler(){
        val rWF = binding.recyclerFavor
        rWF.adapter = FavoriteItemsAdapter(favourites, object: FavoriteItemsAdapter.FavoritesClickListener
        {
            override fun onHeartClick(item: NewItem, position: Int) {
                mainModel.dislike(position)
                rWF.adapter?.notifyItemRemoved(position)
            }
        } )
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouritesFragment()
    }
}