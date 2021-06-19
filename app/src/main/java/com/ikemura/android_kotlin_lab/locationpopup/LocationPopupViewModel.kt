package com.ikemura.android_kotlin_lab.locationpopup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikemura.android_kotlin_lab.common.Event
import com.ikemura.android_kotlin_lab.common.toEvent

class LocationPopupViewModel : ViewModel() {
    private var _state = MutableLiveData<Event<Nav>>()
    val state: LiveData<Event<Nav>>
        get() = _state

    /**
     * メイン処理スタート
     */
    fun startLocationPopup() {
        _state.value = Nav.ShowFineLocationDialog.toEvent()
    }
}

/**
 * 画面の状態
 */
sealed class Nav {
    // フォアグラウンド位置情報の説明Dialogを表示
    object ShowFineLocationDialog : Nav()

    // バックグラウンド位置情報の説明Dialogを表示
    object ShowBackGroundLocationDialog : Nav()

    // フォアグラウンド位置情報の権限リクエスト
    object RequestFineLocation : Nav()

    // バックグラウンド位置情報の権限リクエスト
    object RequestBackGroundLocation : Nav()
}
