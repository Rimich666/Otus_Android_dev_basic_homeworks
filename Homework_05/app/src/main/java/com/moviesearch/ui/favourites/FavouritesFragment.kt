package com.moviesearch.ui.favourites

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.moviesearch.Frags
import com.moviesearch.ui.NewItem
import com.moviesearch.R
import com.moviesearch.databinding.FragmentFavouritesBinding
import com.moviesearch.viewmodel.MainViewModel

class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var mainModel: MainViewModel
    lateinit var host: Host
    private lateinit var favourites: MutableList<NewItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainModel.currFragment = Frags.FAVOR
        favourites = mainModel.favourites.value!!
        mainModel.removeFavourite.observe(this){binding.recyclerFavor.adapter?.notifyItemRemoved(it)}
        mainModel.insertFavourite.observe(this){binding.recyclerFavor.adapter?.notifyItemInserted(it)}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        host = context as Host
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favourites,container,false)
        val view: View = binding.root
        initRecycler()
        return view
    }

    private fun initRecycler(){
        binding.recyclerFavor.adapter = FavoriteItemsAdapter(
            favourites, object: FavoriteItemsAdapter.FavoritesClickListener
            {
                override fun onHeartClick(item: NewItem, position: Int) {
                    host.dislike(item, position)
                }
            }
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouritesFragment()
    }

    interface Host{
        fun dislike(item: NewItem, pos: Int)
    }
}