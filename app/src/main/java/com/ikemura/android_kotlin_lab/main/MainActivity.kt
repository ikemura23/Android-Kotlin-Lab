package com.ikemura.android_kotlin_lab.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.ikemura.android_kotlin_lab.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.TimeoutException

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "例外キャッチ $throwable")
    }
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFragment()
    }

    private fun setupFragment() {
        supportFragmentManager.commit(false) {
            replace(R.id.container, MainFragment.newInstance())
        }
    }

    // エラーの可能性がある処理
    fun doWork(): Deferred<String> = throw TimeoutException()

    fun loadData() {
        scope.launch(exceptionHandler) {
            doWork()
            Log.d(TAG, "到達しない")
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}
