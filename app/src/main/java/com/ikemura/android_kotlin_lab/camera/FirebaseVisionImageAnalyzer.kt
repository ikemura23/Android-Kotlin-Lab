package com.ikemura.android_kotlin_lab.camera

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer

class FirebaseVisionImageAnalyzer : ImageAnalysis.Analyzer {
    private var recognizer: TextRecognizer = TextRecognition.getClient()

    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        Log.d(TAG, "analyze start")
        val mediaImage = imageProxy.image
        mediaImage ?: return

        // imageオブジェクトと回転値を取得
        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        // 文字認識
        recognizer.process(inputImage)
            .addOnSuccessListener {
                Log.d(TAG, it.text)
            }
            .addOnFailureListener {
                Log.e(TAG, it.message)
                it.printStackTrace()
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

    companion object {
        private val TAG = FirebaseVisionImageAnalyzer::class.java.simpleName
    }
}
