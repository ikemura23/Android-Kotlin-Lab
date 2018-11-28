package com.ikemura.android_kotlin_lab

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // 状態
    var state = MutableLiveData<ScreenState>()
    // コルーチンのジョブ
    var job = Job()

    /**
     * データ読み込み
     */
    fun load() {
        state.value = ScreenState.Loading // ローディング表示
        job = GlobalScope.launch(Dispatchers.Main) {
            delay(2000L) // ２秒待つ
            // API通信処理
            val response = SomeData(1) // APIレスポンスを仮作成
            state.value = ScreenState.Data(response) // APIレスポンスを表示
        }
    }
}
