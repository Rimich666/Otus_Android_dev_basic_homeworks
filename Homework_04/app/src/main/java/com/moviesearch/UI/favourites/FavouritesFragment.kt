package com.moviesearch.UI.favourites

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.moviesearch.UI.NewItem
import com.moviesearch.R
import com.moviesearch.viewmodel.Items
import com.moviesearch.viewmodel.MainViewModel

private const val ITEMS = "items"

class FavouritesFragment : Fragment() {
    private var items: Items = Items()
    private var favourites: ArrayList<Int> = arrayListOf()
    private lateinit var recyclerView :RecyclerView
    private lateinit var host: Host

    private lateinit var mainModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainModel.currFragment = "favr"
        arguments?.let {
            items = Items(it.getBundle(ITEMS) as Bundle)
            Log.d("FavouritesFragment", "Список получили: $items")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_favourites, container, false)
        initRecycler(view)
        // Inflate the layout for this fragment
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        host = context as Host
    }

    private fun initRecycler(view: View){
        val layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) GridLayoutManager(view.context, 2) else LinearLayoutManager(view.context)
        recyclerView = view.findViewById(R.id.recycler_favor)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = FavoriteItemsAdapter(items.list, object: FavoriteItemsAdapter.FavoritesClickListener
        {
            override fun onHeartClick(item: NewItem, position: Int) {
                val result = Bundle()
                result.putInt("pos",position)
                parentFragmentManager.setFragmentResult("del_fav", result)
                items.remove(position)
                recyclerView.adapter?.notifyItemRemoved(position)
                host.dislike(position)
            }
        } )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavouritesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Bundle) =
            FavouritesFragment().apply {
                arguments = Bundle().apply {
                    putBundle(ITEMS, param1)
                }
            }
    }

    interface Host{
        fun dislike(pos:Int)
    }
}