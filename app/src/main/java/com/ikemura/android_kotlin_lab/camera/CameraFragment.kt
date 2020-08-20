package com.ikemura.android_kotlin_lab.camera

import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.common.util.concurrent.ListenableFuture
import com.ikemura.android_kotlin_lab.databinding.FragmentCameraBinding
import kotlinx.android.synthetic.main.fragment_camera.viewFinder
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCamera()
    }

    private fun setupCamera() {
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            startCamera(cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun startCamera(cameraProvider: ProcessCameraProvider) {
        // プレビュー
        val preview: Preview = Preview.Builder()
            .build().also {
                it.setSurfaceProvider(viewFinder.createSurfaceProvider())
            }
        // カメラ選択
        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK) // 背面カメラ
            .build()
        cameraProvider.unbindAll()

        // 画像解析
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(720, 1280))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        imageAnalysis.setAnalyzer(cameraExecutor, QrCodeAnalyzer { result ->
            // 画像解析の結果を受け取った
            showDialog(result)
            Log.d("CameraFragment", result)

            requireActivity().runOnUiThread {
                cameraProvider.unbindAll()
            }
        })

        cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, imageAnalysis, preview)
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun showDialog(text: String) {
        requireActivity().runOnUiThread {
            AlertDialog.Builder(requireContext()).setMessage(text).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CameraFragment()
    }
}
