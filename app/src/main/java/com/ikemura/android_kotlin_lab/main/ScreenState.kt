package com.ikemura.android_kotlin_lab.main

/**
 * 画面の状態
 */
sealed class ScreenState {
    // エラー
    object Error : ScreenState()

    // 読み込み中
    object Loading : ScreenState()

    // データ取得完了
    data class Data(val someData: SomeData) : ScreenState()
}

/**
 * データクラス
 */
data class SomeData(val id: Int)
