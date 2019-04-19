package com.ikemura.android_kotlin_lab.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.MainFragmentBinding
import com.otaliastudios.cameraview.Grid
import kotlinx.android.synthetic.main.main_fragment.cameraView
import java.nio.ByteBuffer

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding

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
        setupCameraView()
    }

    private fun setupCameraView() {
        cameraView.setLifecycleOwner(this)
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
            runBarcodeScanner(image)
        }
    }

    private fun runBarcodeScanner(firebaseVisionImage: FirebaseVisionImage) {

        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                    FirebaseVisionBarcode.FORMAT_ALL_FORMATS
                )
                .build()

        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)

        detector.detectInImage(firebaseVisionImage)
                .addOnSuccessListener {
                    for (firebaseBarcode in it) {
                        when (firebaseBarcode.valueType) {
                            FirebaseVisionBarcode.FORMAT_CODE_128 -> firebaseBarcode.displayValue?.let { it1 -> showToast(it1) }
                            else -> firebaseBarcode.displayValue?.let { it1 -> showToast(it1) }
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
                }
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        Log.d("Detected", text)
    }
}
