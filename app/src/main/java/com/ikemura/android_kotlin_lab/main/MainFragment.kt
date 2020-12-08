package com.ikemura.android_kotlin_lab.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.bindings
import com.ikemura.android_kotlin_lab.databinding.MainFragmentBinding
import com.ikemura.android_kotlin_lab.extention.observeEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.main_fragment) {
    private val viewModel: MainViewModel by viewModel()
    private val useBinding by bindings<MainFragmentBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        useBinding.invoke {
            it.button.setOnClickListener {
                viewModel.load()
                // startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))
            }
        }
        // ViewModelの設定
        viewModel.state.observeEvent(this, this::onNavigate)
    }

    private fun onNavigate(state: ScreenState) {
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
    }

    private fun setMessageText(state: String) {
        Log.d("MainFragment", state)
        // binding.message.text = state
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
