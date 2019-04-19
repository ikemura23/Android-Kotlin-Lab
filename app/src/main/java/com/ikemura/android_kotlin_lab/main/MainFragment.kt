package com.ikemura.android_kotlin_lab.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.MainFragmentBinding
import com.ikemura.android_kotlin_lab.next.NextActivity
import com.otaliastudios.cameraview.Grid
import kotlinx.android.synthetic.main.main_fragment.cameraView
import java.nio.ByteBuffer

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

    override fun onResume() {
        super.onResume()
        cameraView.open()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupCameraView()
    }

    /**
     * ViewModel設定
     */
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer(::onNavigated))
    }

    /**
     * カメラ設定
     */
    private fun setupCameraView() {
        cameraView.setLifecycleOwner(viewLifecycleOwner)
        cameraView.grid = Grid.DRAW_3X3
        cameraView.addFrameProcessor { frame ->
            val metadata = FirebaseVisionImageMetadata.Builder()
                    .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                    .setWidth(frame.size.width)
                    .setHeight(frame.size.height)
                    .setRotation(frame.rotation / 90)
                    .build()
            val byteBuffer: ByteBuffer = ByteBuffer.wrap(frame.data)
            val image = FirebaseVisionImage.fromByteBuffer(byteBuffer, metadata)
            viewModel.detectBarcode(image)
        }
    }

    private fun onNavigated(event: MainViewModel.Nav) {
        when (event) {
            is MainViewModel.Nav.GoToNext -> {
                startNextActivity(event.barcodeNumber)
            }
            is MainViewModel.Nav.Failure -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startNextActivity(value: String) {
        val intent = Intent(context, NextActivity::class.java).apply {
            putExtra("barcode", value)
        }
        startActivity(intent)
    }
}
