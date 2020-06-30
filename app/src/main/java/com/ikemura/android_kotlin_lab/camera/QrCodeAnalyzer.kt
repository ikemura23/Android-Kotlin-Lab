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
import com.google.zxing.Result

class QrCodeAnalyzer(
    private val onQrCodeDetected: (qrCode: Result) -> Unit
) : ImageAnalysis.Analyzer {

    private val yuvFormats = mutableListOf(ImageFormat.YUV_420_888)
    val scanner = BarcodeScanning.getClient()

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
        // inputImage.byteBuffer ?: return
        //
        // val image = InputImage.fromByteBuffer(
        //     inputImage.byteBuffer!!,
        //     /* image width */ 480,
        //     /* image height */ 360,
        //     imageProxy.imageInfo.rotationDegrees,
        //     InputImage.IMAGE_FORMAT_NV21 // or IMAGE_FORMAT_YV12
        // )

        scanner.process(inputImage).addOnSuccessListener {
            if(it.isNotEmpty()) {
                Log.d("QrCodeAnalyzer", it.first().displayValue)
            }
            // onQrCodeDetected(it.first())
            // 成功
        }.addOnFailureListener {
            // 失敗
        }.addOnCompleteListener {
            imageProxy.close()
        }

        // val buffer = image.planes[0].buffer
        // buffer.rewind()
        // val byteArray = ByteArray(buffer.remaining())
        // buffer.get(byteArray) // これがないとdecodeエラーになる

        //
        // val source = PlanarYUVLuminanceSource(
        //     byteArray,
        //     image.width,
        //     image.height,
        //     0,
        //     0,
        //     image.width,
        //     image.height,
        //     false
        // )
        // val binarizer = HybridBinarizer(source)
        // val binaryBitmap = BinaryBitmap(binarizer)
        // try {
        //     val result = reader.decode(binaryBitmap)
        //     onQrCodeDetected(result)
        // } catch (e: Exception) {
        //     e.printStackTrace()
        //     Log.e("QRCodeAnalyzer", "QRを認識できない")
        // } finally {
        //     // closeしないと追加の画像の生成がブロックされる（analyzeメソッドが呼ばれない）
        //     image.close()
        // }
    }

    private val reader = MultiFormatReader().apply {
        val map = mapOf(
            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
        )
        setHints(map)
    }
}
