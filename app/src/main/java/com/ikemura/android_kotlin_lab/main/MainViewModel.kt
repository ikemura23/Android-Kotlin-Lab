package com.ikemura.android_kotlin_lab.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

class MainViewModel : ViewModel() {

    // 変数宣言と共に、liveData関数の中に非同期処理を実装できる
    val data: LiveData<String> = liveData {
        // 結果を出力
        emit("Hello")
    }

    val ktxLiveData: LiveData<ScreenState> = liveData {
        emit(ScreenState.Loading)
        val data = doSomeThing()
        emit(ScreenState.Data(data))
    }

    // API通信やDB取得などの処理
    private suspend fun doSomeThing(): SomeData {
        delay(3000L)
        val randomInt = (1..1000).shuffled().first() // 1〜1000の乱数
        return SomeData(randomInt)
    }
}
