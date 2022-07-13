package com.moviesearch.ui.list

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.replace
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.moviesearch.ui.NewItem
import com.moviesearch.R
import com.moviesearch.databinding.FragmentListMovieBinding
import com.moviesearch.trace
import com.moviesearch.ui.date_time_picker.DateTimeDialog
import com.moviesearch.viewmodel.MainViewModel


class ListMovieFragment : Fragment() {
    private lateinit var RW: RecyclerView
    private lateinit var binding :FragmentListMovieBinding
    private lateinit var items: MutableList<NewItem>
    lateinit var host: HostList
    private lateinit var mainModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainModel.currFragment = "list"
        items = mainModel.items.value!!
        mainModel.changeItem.observe(this){
            binding.recyclerView.adapter?.notifyItemChanged(it)
        }
        mainModel.insertItem.observe(this){
            binding.recyclerView.adapter?.notifyItemInserted(it)
        }
        mainModel.deletedItem.observe(this){
            binding.recyclerView.adapter?.notifyItemRemoved(it)
        }
        /*parentFragmentManager.setFragmentResultListener("selectDate", this) { _, bundle ->
            if (bundle.getBoolean("ok")) Log.d("datetime", "${trace()} ${bundle.getString("dateTime")}")
        }*/
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        host = context as HostList
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_movie, container, false)
        val view = binding.root
        RW = view.findViewById(R.id.recycler_view)
        initRecycler()

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListMovieFragment()
    }


    private fun initRecycler(){
        val rW = binding.recyclerView
        val layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            GridLayoutManager(binding.root.context, 2)
        else LinearLayoutManager(binding.root.context)
        rW.layoutManager = layoutManager
        //rW.onScrollStateChanged()
        binding.recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastVisibleItemPosition() == mainModel.items.value?.size!! - 1
                    && dy > 0
                    && !mainModel.loading){
                    //Log.d("scrolling", "${trace()} last: ${mainModel.items.value?.size!! - 1}")
                    //Log.d("scrolling", "${trace()} надо грузить next")
                    mainModel.loading = true
                    host.getNext()
                }

                if (layoutManager.findFirstVisibleItemPosition() == 0
                    && dy < 0
                    && !mainModel.loading){
                    //Log.d("scrolling", "${trace()} firstPos: ${layoutManager.findFirstVisibleItemPosition()}")
                    //Log.d("scrolling", "${trace()} надо грузить previos")
                    mainModel.loading = true
                    host.getPrevious()
                }

            }
        })

        binding.recyclerView.adapter = NewItemsAdapter(mainModel.items.value as List<NewItem>,
            object: NewItemsAdapter.DetailClickListener
            {
                override fun onDetailClick(newsItem: NewItem, position: Int) {
                    host.showDetail(position)
                }

                override fun onItemLongClick(newsItem: NewItem, position: Int):Boolean{
                    host.likedItem(newsItem, position)
                    return true
                }

                override fun onHeartClick(newsItem: NewItem, position: Int) {
                    host.likedItem(newsItem, position)
                }

                override fun onDeferClick(newsItem: NewItem, position: Int) {
                    val dialog = DateTimeDialog.newInstance()
                    parentFragmentManager.setFragmentResultListener("selectDate", this@ListMovieFragment) {
                            _, bundle ->
                        if (bundle.getBoolean("ok")) host.defer(newsItem, position, bundle.getString("dateTime")!!)
                    }
                    dialog.show(activity!!.supportFragmentManager, DateTimeDialog.TAG)

                }


            }
        )
    }

    interface HostList{
        fun showDetail(position: Int)
        fun likedItem(item: NewItem, position: Int)
        fun defer(item: NewItem, position: Int, dateTime: String)
        fun getNext()
        fun getPrevious()
    }
}