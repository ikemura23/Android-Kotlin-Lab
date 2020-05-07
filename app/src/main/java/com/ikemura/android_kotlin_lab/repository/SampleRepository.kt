package com.ikemura.android_kotlin_lab.repository

import com.ikemura.android_kotlin_lab.main.SomeData
import kotlinx.coroutines.delay

class SampleRepository : ISampleRepository {

    override suspend fun load(): SomeData {
        delay(2000L) // ２秒待つ
        // API通信処理
        return SomeData(1, "hello") // APIレスポンスを仮作成
    }
}

interface ISampleRepository {
    suspend fun load(): SomeData
}
