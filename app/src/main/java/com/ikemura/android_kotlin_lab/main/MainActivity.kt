package com.ikemura.android_kotlin_lab.main

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.camera.CameraFragment

class MainActivity : AppCompatActivity() {

    private val askMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            // リクエストだけして許可/拒否の結果は無視する
            for (entry in map.entries) {
                println("askMultiplePermissions: \"${entry.key} = ${entry.value}\"")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()
        setupFragment()
    }

    private fun setupFragment() {
        supportFragmentManager.commit(false) {
            replace(R.id.container, CameraFragment.newInstance())
        }
    }

    /**
     * 権限リクエスト
     */
    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA
        )
        askMultiplePermissions.launch(permissions)
    }
}
