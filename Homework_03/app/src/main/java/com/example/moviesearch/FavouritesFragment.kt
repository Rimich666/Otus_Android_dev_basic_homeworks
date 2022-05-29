package com.example.moviesearch

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ITEMS = "items"
private const val FAVOR = "favourites"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouritesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var items: Items = Items()
    private var favourites: ArrayList<Int> = arrayListOf()
    private lateinit var recyclerView :RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            items = Items(it.getBundle(ITEMS) as Bundle)
            Log.d("FavouritesFragment", "Список получили: $items")
            favourites = it.getIntegerArrayList(FAVOR) as ArrayList<Int>
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

    private fun initRecycler(view: View){
        val layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) GridLayoutManager(view.context, 2) else LinearLayoutManager(view.context)
        recyclerView = view.findViewById(R.id.recycler_favor)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = FavoriteItemsAdapter(items.list, object:FavoriteItemsAdapter.FavoritesClickListener
        {
            override fun onHeartClick(item: NewItem, position: Int) {
                val result = Bundle()
                result.putInt("pos",position)
                parentFragmentManager.setFragmentResult("del_fav", result)
                items.remove(position)
                recyclerView.adapter?.notifyItemRemoved(position)
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
        fun newInstance(param1: Bundle, param2: ArrayList<Int>) =
            FavouritesFragment().apply {
                arguments = Bundle().apply {
                    putBundle(ITEMS, param1)
                    putIntegerArrayList(FAVOR, param2)
                }
            }
    }
}