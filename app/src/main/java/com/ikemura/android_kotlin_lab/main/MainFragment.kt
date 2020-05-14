package com.ikemura.android_kotlin_lab.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.MainFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        binding.button.setOnClickListener {
            viewModel.load()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewModel() {
        // 状態の管理
        viewModel.state.observe(viewLifecycleOwner, Observer<ScreenState> { state ->
            when (state) {
                is ScreenState.Loading -> {
                    //ローディング処理
                    setMessageText("Loading")
                }
                is ScreenState.Data -> {
                    //データ取得
                    setMessageText("Success: ${state.someData}")
                    Toast.makeText(context, "Success: ${state.someData}", Toast.LENGTH_SHORT).show()
                }
                is ScreenState.Error -> {
                    //エラー処理
                    Log.d("MainFragment", "Error")
                    setMessageText("Error")
                }
            }
        })
    }

    private fun setMessageText(state: String) {
        Log.d("MainFragment", state)
        binding.message.text = state
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
