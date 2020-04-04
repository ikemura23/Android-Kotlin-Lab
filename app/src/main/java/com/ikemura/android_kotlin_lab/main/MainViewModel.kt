package com.ikemura.android_kotlin_lab.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // 状態（viewModel専用）
    private var _state = MutableLiveData<ScreenState>()

    // 外部公開用
    val state = Transformations.distinctUntilChanged(_state)

    val ktxLiveData: LiveData<ScreenState> = liveData {
        emit(ScreenState.Loading)
        val data = doSomeThing()
        emit(ScreenState.Data(data))
    }

    // API通信やDB取得などの処理
    private suspend fun doSomeThing(): SomeData {
        delay(3000L)
        return SomeData(123)
    }

    // データ読み込み
    fun load() {
        _state.value = ScreenState.Loading // ローディング表示
        viewModelScope.launch(Dispatchers.Main) {
            try {
                delay(2000L) // ２秒待つ
                // API通信処理
                val response = SomeData(1) // APIレスポンスを仮作成
                _state.value = ScreenState.Data(response) // APIレスポンスを表示
            } catch (e: Exception) {
                _state.value = ScreenState.Error // エラー
            }
        }
    }
}
