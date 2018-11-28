package com.ikemura.android_kotlin_lab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.main_fragment.text

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
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
                    text.text = state.someData.id.toString()
                }
                is ScreenState.Error -> {
                    //エラー処理
                    Log.d("MainFragment", "Error")
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // 呼び出し
        viewModel.load()
    }

    override fun onStop() {
        super.onStop()
        // キャンセル
        viewModel.job.cancel()
    }
}
