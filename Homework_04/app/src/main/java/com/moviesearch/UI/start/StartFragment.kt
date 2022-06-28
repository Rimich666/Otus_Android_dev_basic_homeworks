package com.moviesearch.UI.start

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.moviesearch.R
import com.moviesearch.databinding.FragmentStartBinding
import com.moviesearch.trace
import com.moviesearch.viewmodel.MainViewModel

class StartFragment : Fragment() {
    private lateinit var mainModel: MainViewModel
    private lateinit var binding: FragmentStartBinding
    private lateinit var items: MutableList<StartItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        //startModel = ViewModelProvider(this)[StartViewModel::class.java]
        mainModel.currFragment = "start"
        items = mainModel.requestedItems.value!!
        mainModel.progress.observe(this) { binding.progressHorizontal.progress = it }
        mainModel.requestedInserted.observe(this){
            //Log.d("start", "${trace()} Запрос страницы $it")
            binding.recyclerStart.adapter!!.notifyItemInserted(it)
        }
        mainModel.requestedItems.observe(this){binding.recyclerStart.adapter!!.notifyDataSetChanged()}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        initRecycler()
        return binding.root
    }

    private fun initRecycler(){
        binding.recyclerStart.adapter = StartItemAdapter(items)
    }

    companion object {
        @JvmStatic
        fun newInstance() = StartFragment()
    }
}