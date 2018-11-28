package com.ikemura.android_kotlin_lab

sealed class ScreenState {
    object Error : ScreenState()
    object Loading : ScreenState()
    data class Data(val someData: SomeData) : ScreenState()
}

data class SomeData(val id: Int)
