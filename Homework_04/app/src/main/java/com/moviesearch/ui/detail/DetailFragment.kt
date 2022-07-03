package com.moviesearch.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.moviesearch.R
import com.moviesearch.databinding.FragmentDetailBinding
import com.moviesearch.viewmodel.MainViewModel

class DetailFragment : androidx.fragment.app.Fragment() {

    private lateinit var mainModel: MainViewModel
    private lateinit var binding: FragmentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainModel.currFragment = "detl"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_, container, false)
        val view = binding.root
        val item = mainModel.items.value!![mainModel.selectedPosition]
        Glide.with(this)
            .load(item.poster)
            .into(binding.mainBackdrop)
        binding.mainToolbar.title = item.name
        binding.detailsText.text = mainModel.detailsText

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() = DetailFragment()
        }
    }
