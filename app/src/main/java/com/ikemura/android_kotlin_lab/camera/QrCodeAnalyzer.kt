package com.ikemura.android_kotlin_lab.camera

import android.annotation.SuppressLint
import android.graphics.ImageFormat
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class QrCodeAnalyzer : ImageAnalysis.Analyzer {
    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(image: ImageProxy) {
        image.image?.let {
            if (it.format == ImageFormat.YUV_420_888) {
                Log.d("image_format", "YUV_420_888")
            }
        }
    }
}
