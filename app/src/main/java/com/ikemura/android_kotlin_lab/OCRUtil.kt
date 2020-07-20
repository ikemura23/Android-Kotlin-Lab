package com.ikemura.android_kotlin_lab

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class OCRUtil(context: Context) {

    private val TESS_DATA_DIR = "tessdata" + File.separator
    private val TESS_TRAINED_DATA = arrayListOf("jpn.traineddata")

    init {
        checkTrainedData(context)
    }

    private fun checkTrainedData(context: Context) {
        val dataPath = context.filesDir.toString() + File.separator + TESS_DATA_DIR
        val dir = File(dataPath)
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(context)
        }

        if (dir.exists()) {
            TESS_TRAINED_DATA.forEach {
                val dataFilePath = dataPath + it
                val datafile = File(dataFilePath)
                if (!datafile.exists()) {
                    copyFiles(context)
                }
            }
        }
    }

    private fun copyFiles(context: Context) {
        try {
            TESS_TRAINED_DATA.forEach {
                val filePath = context.filesDir.toString() + File.separator + TESS_DATA_DIR + it

                context.assets.open(TESS_DATA_DIR + it).use { inputStream ->
                    FileOutputStream(filePath).use { outStream ->
                        val buffer = ByteArray(1024)
                        var read = inputStream.read(buffer)
                        while (read != -1) {
                            outStream.write(buffer, 0, read)
                            read = inputStream.read(buffer)
                        }
                        outStream.flush()
                    }
                }

                val file = File(filePath)
                if (!file.exists()) throw FileNotFoundException()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getString(context: Context, bitmap: Bitmap, lang: String): String {
        val baseApi = TessBaseAPI()
        baseApi.init(context.getFilesDir().toString(), lang)
        baseApi.setImage(bitmap)
        val recognizedText = baseApi.utF8Text
        baseApi.end()

        return recognizedText
    }

    companion object {
        enum class LangType constructor(val str: String) {
            jpn("jpn"),
            jpnnew("jpnnew"),
            eng("eng"),
            UNKNOWN("eng");

            companion object {

                fun getLangType(str: String): LangType {
                    val types = LangType.values()
                    for (type in types) {
                        if (type.str.equals(str)) {
                            return type
                        }
                    }
                    return UNKNOWN
                }
            }
        }
    }
}
