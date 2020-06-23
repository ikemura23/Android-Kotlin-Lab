package com.ikemura.android_kotlin_lab.camera

import android.os.Bundle
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
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var executor: Executor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        executor = Executors.newSingleThreadExecutor()
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder()
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        cameraProvider.unbindAll()

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(720, 1280))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(executor, FirebaseVisionImageAnalyzer())

        // imageAnalysis.setAnalyzer(executor, QrCodeAnalyzer { result ->
        //     Log.d("CameraFragment", result.text)
        //     showDialog(result.text)
        //
        //     requireActivity().runOnUiThread {
        //         cameraProvider.unbindAll()
        //     }
        // })

        cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, imageAnalysis, preview)
        preview.setSurfaceProvider(viewFinder.createSurfaceProvider())
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
