package com.ikemura.android_kotlin_lab.camera

import android.annotation.SuppressLint
import android.graphics.ImageFormat
import android.os.Build
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.Result
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

class QrCodeAnalyzer(
    private val onQrCodeDetected: (qrCode: Result) -> Unit
) : ImageAnalysis.Analyzer {

    private val yuvFormats = mutableListOf(ImageFormat.YUV_420_888)

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            yuvFormats.addAll(listOf(ImageFormat.YUV_422_888, ImageFormat.YUV_444_888))
        }
    }

    // private fun ByteBuffer.toByteArray(): ByteArray {
    //     rewind()
    //     val data = ByteArray(remaining())
    //     get(data)
    //     return data
    // }


    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(image: ImageProxy) {
        // if (image.format !in yuvFormats) {
        //     Log.e("QRCodeAnalyzer", "Expected YUV, now = ${image.format}")
        //     return
        // }
        // val data = image.planes[0].buffer.toByteArray()

        val buffer = image.planes[0].buffer
        buffer.rewind()
        val byteArray = ByteArray(buffer.remaining())
        buffer.get(byteArray)
        // val byteBuffer = buffer.get(data)

        val source = PlanarYUVLuminanceSource(
            byteArray,
            image.width,
            image.height,
            0,
            0,
            image.width,
            image.height,
            false
        )
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
        try {
            val result = reader.decode(binaryBitmap)
            onQrCodeDetected(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val reader = MultiFormatReader().apply {
        val map = mapOf(
            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
        )
        setHints(map)
    }
}
