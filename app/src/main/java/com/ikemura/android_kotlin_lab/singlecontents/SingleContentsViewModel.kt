package com.ikemura.android_kotlin_lab.singlecontents

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikemura.android_kotlin_lab.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SingleContentsViewModel : ViewModel() {

    private var _state = MutableLiveData<ScreenState>()
    val state = Transformations.distinctUntilChanged(_state)

    // データ読み込み
    fun load() {
        _state.value = ScreenState.Loading // ローディング表示
        viewModelScope.launch(Dispatchers.Main) {
            try {
                delay(2000L) // ２秒待つ
                // API通信処理
                val response = Data("100", "Billy", "hello world") // APIレスポンスを仮作成
                _state.value = ScreenState.Success(response) // APIレスポンスを表示
            } catch (e: Exception) {
                _state.value = ScreenState.Error // エラー
            }
        }
    }

    /**
     * 画面の状態
     */
    sealed class ScreenState {
        // エラー
        object Error : ScreenState()

        // 読み込み中
        object Loading : ScreenState()

        // データ取得完了
        data class Success(val data: Data) : ScreenState()
    }
}
