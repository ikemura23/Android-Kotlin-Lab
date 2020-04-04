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

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    private fun setupViewModel() {
        // 状態の管理
        viewModel.state.observe(viewLifecycleOwner, Observer<ScreenState> { state ->
            when (state) {
                is ScreenState.Loading -> {
                    //ローディング処理
                    Log.d("MainFragment", "Loading")
                }
                is ScreenState.Data -> {
                    //データ取得
                    Log.d("MainFragment", state.someData.toString())
                    binding.message.text = state.someData.id.toString()
                }
                is ScreenState.Error -> {
                    //エラー処理
                    Log.d("MainFragment", "Error")
                }
            }
        })
    }
}
