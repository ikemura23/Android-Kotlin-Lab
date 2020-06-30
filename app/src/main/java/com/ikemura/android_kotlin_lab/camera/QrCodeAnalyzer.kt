package com.ikemura.android_kotlin_lab.camera

import android.annotation.SuppressLint
import android.graphics.ImageFormat
import android.os.Build
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.google.zxing.BarcodeFormat
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader

class QrCodeAnalyzer(
    private val onQrCodeDetected: (qrCodeString: String) -> Unit
) : ImageAnalysis.Analyzer {

    private val yuvFormats = mutableListOf(ImageFormat.YUV_420_888)
    private val scanner = BarcodeScanning.getClient()

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            yuvFormats.addAll(listOf(ImageFormat.YUV_422_888, ImageFormat.YUV_444_888))
        }
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        if (imageProxy.format !in yuvFormats) {
            Log.e("QRCodeAnalyzer", "Expected YUV, now = ${imageProxy.format}")
            return
        }
        val mediaImage = imageProxy.image
        mediaImage ?: return
        val inputImage: InputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        scanner.process(inputImage).addOnSuccessListener {
            // 成功
            if (it.isNotEmpty()) {
                it.first().displayValue?.let { value ->
                    Log.d("QrCodeAnalyzer", value)
                    onQrCodeDetected(value)
                }
            }
        }.addOnFailureListener {
            // 失敗
            it.printStackTrace()
        }.addOnCompleteListener {
            imageProxy.close()
        }
    }

    private val reader = MultiFormatReader().apply {
        val map = mapOf(
            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
        )
        setHints(map)
    }
}
