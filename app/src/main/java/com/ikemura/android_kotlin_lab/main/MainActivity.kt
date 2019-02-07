package com.ikemura.android_kotlin_lab.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.list.ItemFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFragment()
    }

    private fun setupFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SampleFragment())
                .commitNow()
    }
}
