package com.ikemura.android_kotlin_lab.next

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ikemura.android_kotlin_lab.R
import kotlinx.android.synthetic.main.activity_next.number

class NextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
        intent.getStringExtra("barcode")?.let { number.text = it }
    }
}
