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
        Log.d(TAG, "analyze")
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            // imageオブジェクトと回転値を取得
            val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
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

            // val byteBuffer = imageProxy.planes[0].buffer
            // byteBuffer.rewind()
            // val byteArray = ByteArray(byteBuffer.remaining())
            // byteBuffer.get(byteArray) // これがないとdecodeエラーになる
            //
            // val image = InputImage.fromByteBuffer(
            //     byteBuffer,
            //     /* image width */ 480,
            //     /* image height */ 360,
            //     inputImage.rotationDegrees,
            //     InputImage.IMAGE_FORMAT_YUV_420_888 // or IMAGE_FORMAT_YV12
            // )
        }
    }

    companion object {
        private val TAG = FirebaseVisionImageAnalyzer::class.java.simpleName
    }
}
