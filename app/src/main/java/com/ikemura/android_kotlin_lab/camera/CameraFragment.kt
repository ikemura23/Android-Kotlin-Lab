package com.ikemura.android_kotlin_lab.camera

import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.ikemura.android_kotlin_lab.OCRUtil
import com.ikemura.android_kotlin_lab.databinding.FragmentCameraBinding
import kotlinx.android.synthetic.main.fragment_camera.viewFinder
import java.util.concurrent.Executors

class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOCR()
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }
    lateinit var ocrUtil: OCRUtil
    private fun setupOCR() {
        ocrUtil = OCRUtil(requireActivity().applicationContext)

    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder()
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        // val baseApi = TessBaseAPI().also {
        //     it.init(requireContext().filesDir.toString(), "jpn")
        // }
        val tessTwoAnalyzer = TessTwoAnalyzer(ocrUtil,requireContext()) { text ->
            Log.d("CameraFragment", text)
        }
        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), tessTwoAnalyzer)

        cameraProvider.unbindAll()
        val camera: Camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview, imageAnalysis)
        preview.setSurfaceProvider(viewFinder.createSurfaceProvider())
    }

    companion object {
        @JvmStatic
        fun newInstance() = CameraFragment()
        val TAG = CameraFragment::class.java.simpleName
    }
}
