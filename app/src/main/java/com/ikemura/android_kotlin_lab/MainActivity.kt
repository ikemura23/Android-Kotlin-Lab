package com.ikemura.android_kotlin_lab

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.identifier.AdvertisingIdClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread(Runnable {
            Log.d("MainActivity", getAdvertiseId(this))
        }).start()
    }
}

fun getAdvertiseId(
        context: Context) = AdvertisingIdClient.getAdvertisingIdInfo(context).id.toString()

