package com.moviesearch.UI.list

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.moviesearch.UI.NewItem
import com.moviesearch.R
import com.moviesearch.viewmodel.Items


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val SELP = "selectedPosition"
private const val ITEMS = "items"
private const val FAVOR = "favorites"

/**
 * A simple [Fragment] subclass.
 * Use the [ListMovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListMovieFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var recyclerView: RecyclerView
    private lateinit var items: Items
    private var favourites: ArrayList<Int> = arrayListOf()
    private var selectedPosition: Int = -1
    lateinit var host: Host

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            items = Items(it.getBundle(ITEMS) as Bundle)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        host = context as Host
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_movie, container, false)
        view.findViewById<RecyclerView>(R.id.recycler_view)
        initRecycler(view)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListMovieFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Bundle, fav: ArrayList<Int>) =
            ListMovieFragment().apply {
                arguments = Bundle().apply {
                    putBundle(ITEMS, param1)
                    putIntegerArrayList(FAVOR, fav)
                }
            }
    }

    private fun initRecycler(view: View){
        val layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) GridLayoutManager(
            view.context, 2) else LinearLayoutManager(view.context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = NewItemsAdapter(items.list, object: NewItemsAdapter.DetailClickListener
        {
            override fun onDetailClick(newsItem: NewItem, position: Int) {
                 host.showDetail(position, items[position].bundle)
            }

            override fun onItemLongClick(newsItem: NewItem, position: Int):Boolean{
                changeFavourites(newsItem,position)
                return true
            }

            override fun onHeartClick(newsItem: NewItem, position: Int) {
                changeFavourites(newsItem,position)
            }
        } )
    }

    private fun changeFavourites(item: NewItem, pos: Int){
        if (item.liked){ favourites.remove(pos) }
        else{favourites.add(pos)}
        item.liked = !item.liked
        recyclerView.adapter?.notifyItemChanged(pos)
        host.likedItem(pos, item.liked)
        }


    interface Host{
        fun showDetail(position: Int, item: Bundle)
        fun likedItem(position: Int, liked: Boolean)
    }

}