package com.ikemura.android_kotlin_lab.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikemura.android_kotlin_lab.common.Event
import com.ikemura.android_kotlin_lab.common.toEvent
import com.ikemura.android_kotlin_lab.repository.ISampleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val repository: ISampleRepository) : ViewModel() {
    // 状態（viewModel専用）
    private var _state = MutableLiveData<Event<ScreenState>>()

    // 外部公開用
    val state: LiveData<Event<ScreenState>>
        get() = _state

    // データ読み込み
    fun load() {
        _state.value = ScreenState.Loading.toEvent() // ローディング表示
        viewModelScope.launch(Dispatchers.Main) {
            try {
                // API通信処理
                val response = repository.load()
                _state.value = ScreenState.Data(response).toEvent() // APIレスポンスを表示
            } catch (e: Exception) {
                _state.value = ScreenState.Error.toEvent() // エラー
            }
        }
    }
}
