package com.ikemura.android_kotlin_lab.singlecontents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ikemura.android_kotlin_lab.R

class SingleContentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_contents_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SingleContentsFragment.newInstance())
                .commitNow()
        }
    }
}
