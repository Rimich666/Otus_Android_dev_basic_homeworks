package com.moviesearch.UI.list

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviesearch.MainActivity

import com.moviesearch.UI.NewItem
import com.moviesearch.R
import com.moviesearch.databinding.FragmentListMovieBinding
import com.moviesearch.viewmodel.MainViewModel


class ListMovieFragment : Fragment() {

    private lateinit var binding :FragmentListMovieBinding
    private lateinit var items: MutableList<NewItem>
    lateinit var host: Host
    private lateinit var mainModel: MainViewModel


    private lateinit var recyclerView: RecyclerView
    private var favourites: ArrayList<Int> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainModel.currFragment = "list"
        items = mainModel.items.value!!
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        host = context as Host
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_movie, container, false)
        val view = binding.root
        initRecycler()
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListMovieFragment()
    }

    private fun initRecycler(){
        binding.recyclerView.adapter = NewItemsAdapter(mainModel.items.value as List<NewItem>,
            object: NewItemsAdapter.DetailClickListener
        {
            override fun onDetailClick(newsItem: NewItem, position: Int) {
                 host.showDetail(position)
            }

            override fun onItemLongClick(newsItem: NewItem, position: Int):Boolean{
                changeLiked(newsItem,position)
                return true
            }

            override fun onHeartClick(newsItem: NewItem, position: Int) {
                changeLiked(newsItem,position)
            }
        })
    }

    private fun changeLiked(item: NewItem, pos: Int){
        host.likedItem(pos, !item.liked, item)
    }

    interface Host{
        fun showDetail(position: Int)
        fun likedItem(position: Int, liked: Boolean, item: NewItem)
    }
}