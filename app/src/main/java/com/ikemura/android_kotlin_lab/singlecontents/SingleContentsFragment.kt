package com.ikemura.android_kotlin_lab.singlecontents

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
import com.ikemura.android_kotlin_lab.databinding.SingleContentsFragmentBinding

class SingleContentsFragment : Fragment() {

    private lateinit var binding: SingleContentsFragmentBinding
    private lateinit var viewModel: SingleContentsViewModel
    private lateinit var contoller: SingleContentsController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.single_contents_fragment, container, false)
        contoller = SingleContentsController()
        binding.recyclerView.adapter = contoller.adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SingleContentsViewModel::class.java)
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is SingleContentsViewModel.ScreenState.Loading -> Log.d("SingleContentsFragment", "Loading")
                is SingleContentsViewModel.ScreenState.Success -> {
                    Log.d("SingleContentsFragment", "Success ${state.data}")
                    contoller.setData("Success", state.data)
                }
                is SingleContentsViewModel.ScreenState.Error -> Log.d("SingleContentsFragment", "Error")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    companion object {
        fun newInstance() = SingleContentsFragment()
    }
}
