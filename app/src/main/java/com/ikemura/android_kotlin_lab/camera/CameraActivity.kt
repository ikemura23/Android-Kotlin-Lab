package com.ikemura.android_kotlin_lab.camera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ikemura.android_kotlin_lab.R

class CameraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CameraFragment.newInstance())
            .commitNow()
    }
}
