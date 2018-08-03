package com.ikemura.android_kotlin_lab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.star
import kotlinx.android.synthetic.main.activity_main.star_layout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupClickEvent()
    }

    private fun setupClickEvent() {
        star_layout.setOnClickListener {
            star.playAnimation()
        }
    }
}
