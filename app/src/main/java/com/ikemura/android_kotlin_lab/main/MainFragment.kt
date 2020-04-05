package com.ikemura.android_kotlin_lab.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
    }

    private fun loadKtxLiveData() {
        // viewModel.data.observe(viewLifecycleOwner, Observer { data ->
        //     Log.d("MainFragment", data)
        //     binding.message.text = data
        // })
        // // 2回購読しても大丈夫
        // viewModel.data.observe(viewLifecycleOwner, Observer { data ->
        //     Log.d("MainFragment", data)
        //     binding.message.text = data
        // })

        viewModel.ktxLiveData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is ScreenState.Loading -> {
                    binding.stateMessage.text = "Loading"
                    Log.d("MainFragment", "Loading")
                }
                is ScreenState.Data -> {
                    Log.d("MainFragment", "Data: ${state.someData}")
                    binding.stateMessage.text = state.someData.id.toString()
                    Log.d("MainFragment", "Finish")
                }
                is ScreenState.Error -> {
                    // Error handling
                    Log.d("MainFragment", "Error")
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadKtxLiveData()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
