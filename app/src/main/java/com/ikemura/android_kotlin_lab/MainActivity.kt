package com.ikemura.android_kotlin_lab

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.android.synthetic.main.activity_main.button1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ボタンを押したら
        button1.setOnClickListener {
            startWorkManager()
        }
    }

    // WorkManagerを実行
    private fun startWorkManager() {
        //制約を作成
        val constraints = Constraints.Builder()
                .setRequiresCharging(true)      //充電中である
                .setRequiresBatteryNotLow(true) //バッテリーが少なくない
                .build()
        //worker requestを作成
        val testWorker = OneTimeWorkRequestBuilder<TestWorker>()
                .setConstraints(constraints)    //制約を追加
                .build()
        //タスクを監視
        WorkManager.getInstance().getStatusById(testWorker.id).observe(this, Observer {
            if (it == null) return@Observer
            Log.d("MainActivity:StatusById", it.toString())
        })
        //タスクをエンキュー
        WorkManager.getInstance().enqueue(testWorker)
        WorkManager.getInstance().cancelWorkById(testWorker.id)
    }
}

// WorkManagerクラス
class TestWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        // ここに処理内容を書く
        Log.d("MainActivity", "WorkManager Test")
        return Result.SUCCESS
    }
}