package com.ikemura.android_kotlin_lab

import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.activity_main.main_container

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, FirstFragment())
                .commit()

        fab.setOnClickListener {
            val transition = main_container.background as TransitionDrawable
            transition.startTransition(1000)

            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, SecondFragment())
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .commit()
        }
    }

    override fun onBackPressed() {
        val transition = main_container.background as TransitionDrawable
        transition.reverseTransition(1000)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, FirstFragment())
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .commit()
    }
}
