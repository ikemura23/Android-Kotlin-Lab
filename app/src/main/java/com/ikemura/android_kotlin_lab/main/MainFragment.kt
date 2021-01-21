package com.ikemura.android_kotlin_lab.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.bindings
import com.ikemura.android_kotlin_lab.camera.CameraActivity
import com.ikemura.android_kotlin_lab.databinding.MainFragmentBinding
import com.ikemura.android_kotlin_lab.databinding.PagerFragmentBinding
import com.ikemura.android_kotlin_lab.extention.observeEvent
import com.ikemura.android_kotlin_lab.sub.SubActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.main_fragment) {
    private val viewModel: MainViewModel by viewModel()
    private val useBinding by bindings<MainFragmentBinding>()
    private val subLauncher = registerForActivityResult(SubActivity.SubActivityResultContract()) {
        it?.run { setMessageText(it) }
    }
    private val subSealedLauncher = registerForActivityResult(SubActivity.SubActivitySealedResultContract(), this::bindResult)
    private fun bindResult(out: SubActivity.SubActivitySealedResultContract.Out) {
        when (out) {
            is SubActivity.SubActivitySealedResultContract.Out.hoge -> setMessageText("hoge")
            is SubActivity.SubActivitySealedResultContract.Out.fuga -> setMessageText(out.str)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        useBinding.invoke {
            it.stateButton.setOnClickListener {
                viewModel.load()
            }
            it.activityButton.setOnClickListener {
                navigateSubActivity()
            }
            it.cameraButton.setOnClickListener {
                navigateCamera()
            }
            it.cameraButton.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionCamera())
            }
            it.tabButton.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToPagerFragment())
            }
        }
        // ViewModelの設定
        viewModel.state.observeEvent(this, this::onNavigate)
    }

    private fun navigateSubActivity() {
        // subLauncher.launch(Unit)
        subSealedLauncher.launch(SubActivity.SubActivitySealedResultContract.InputMyType.Type2)
    }

    private fun navigateCamera() {
        startActivity(Intent(requireContext(), CameraActivity::class.java))
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
        useBinding {
            it.message.text = state
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
