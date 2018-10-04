package com.ikemura.android_kotlin_lab

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.android.synthetic.main.activity_main.button1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {
            startWorkManager()
        }
    }

    private fun startWorkManager() {
        val testWorker = OneTimeWorkRequestBuilder<TestWorker>().build()
        WorkManager.getInstance().enqueue(testWorker)
    }
}

class TestWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d("MainActivity", "WorkManager Test")
        return Result.SUCCESS
    }
}