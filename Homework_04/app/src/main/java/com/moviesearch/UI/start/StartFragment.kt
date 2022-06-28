package com.moviesearch.UI.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.moviesearch.R
import com.moviesearch.databinding.FragmentStartBinding
import com.moviesearch.viewmodel.MainViewModel

class StartFragment : Fragment() {
    private lateinit var mainModel: MainViewModel
    private lateinit var binding: FragmentStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainModel.currFragment = "start"
        mainModel.progress.observe(this) { binding.progressHorizontal.progress = it }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = StartFragment()
    }
}