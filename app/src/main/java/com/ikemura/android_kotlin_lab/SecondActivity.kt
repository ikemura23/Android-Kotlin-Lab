package com.ikemura.android_kotlin_lab

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setResult(Activity.RESULT_OK)
        finish()
    }
}
