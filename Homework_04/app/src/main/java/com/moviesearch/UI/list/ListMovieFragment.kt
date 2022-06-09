package com.moviesearch.UI.list

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.moviesearch.UI.NewItem
import com.moviesearch.R
import com.moviesearch.viewmodel.Items
import com.moviesearch.viewmodel.MainViewModel


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

    private lateinit var recyclerView: RecyclerView
    private lateinit var items: Items
    private var favourites: ArrayList<Int> = arrayListOf()
    private var selectedPosition: Int = -1
    lateinit var host: Host

    private lateinit var mainModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainModel.currFragment = "list"
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
        val view = inflater.inflate(R.layout.fragment_list_movie, container, false)
        view.findViewById<RecyclerView>(R.id.recycler_view)
        initRecycler(view)
        return view
    }

    companion object {
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
        recyclerView = view.findViewById(R.id.recycler_view)
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