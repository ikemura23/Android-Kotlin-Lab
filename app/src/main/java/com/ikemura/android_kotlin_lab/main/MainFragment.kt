package com.ikemura.android_kotlin_lab.main

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.MainFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setupViewModel()
        binding.button.setOnClickListener {
            //            showPopupWindow()
            binding.fukidasi.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.Main) {
                delay(2000)
                val alphaAnimation = AlphaAnimation(1f, 0f).apply { duration = 1000 }
                binding.fukidasi.startAnimation(alphaAnimation)
                delay(1000)
                binding.fukidasi.visibility = View.GONE
            }
        }
    }

    private fun showPopupWindow() {
        val inflater = requireActivity().getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_window, null)
        val width = FrameLayout.LayoutParams.WRAP_CONTENT
        val height = FrameLayout.LayoutParams.WRAP_CONTENT
        val rect = locateView(binding.button)
        PopupWindow(popupView, width, height).showAtLocation(binding.button, Gravity.NO_GRAVITY, rect.left, rect.top - binding.button.height)
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    private fun locateView(v: View): Rect {
        val ints = IntArray(2)
        v.getLocationOnScreen(ints)
        val location = Rect()
        location.left = ints[0]
        location.top = ints[1]
        location.right = location.left + v.width
        location.bottom = location.top + v.height
        return location
    }

    private fun setupViewModel() {
        // 状態の管理
        viewModel.state.observe(this, Observer<ScreenState> { state ->
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
