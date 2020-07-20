package com.ikemura.android_kotlin_lab.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.ikemura.android_kotlin_lab.OCRUtil
import java.io.ByteArrayOutputStream

class TessTwoAnalyzer(
    private val ocrUtil: OCRUtil,
    private val context: Context,
    private val onTextDetected: (text: String) -> Unit
) : ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {
        val bitmap = image.toBitmap()

        try {
            val text = ocrUtil.getString(context, bitmap, OCRUtil.Companion.LangType.getLangType("jpn").str)
            onTextDetected(text)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        image.close()
    }

    /**
     * https://stackoverflow.com/questions/56772967/converting-imageproxy-to-bitmap
     */
    // private fun ImageProxy.toBitmap(): Bitmap {
    //     val buffer = this.planes[0].buffer
    //     buffer.rewind()
    //     val bytes = ByteArray(buffer.capacity())
    //     buffer.get(bytes) // これがないとdecodeエラーになる
    //
    //     // https://stackoverflow.com/questions/56772967/converting-imageproxy-to-bitmap
    //     return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    // }
    fun ImageProxy.toBitmap(): Bitmap {
        val yBuffer = planes[0].buffer // Y
        val uBuffer = planes[1].buffer // U
        val vBuffer = planes[2].buffer // V

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        //U and V are swapped
        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}
