package com.ikemura.android_kotlin_lab

import android.graphics.ImageDecoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.image

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupImageDecoder()
    }

    /**
     * @see https://developer.android.com/about/versions/pie/android-9.0?hl=ja
     * @see https://developer.android.com/reference/android/graphics/ImageDecoder?hl=ja
     */
    private fun setupImageDecoder() {
        val src = ImageDecoder.createSource(resources.assets, "droid.jpeg")
        val drawable = ImageDecoder.decodeDrawable(src)

        image.setImageDrawable(drawable)
    }
}
