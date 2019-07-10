package com.ikemura.android_kotlin_lab.main

import UserQuery
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MainViewModel : ViewModel() {
    // 状態（viewModel専用）
    private var _state = MutableLiveData<ScreenState>()

    // 外部公開用
    val state = Transformations.distinctUntilChanged(_state)

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

    fun loadGraphql() {
        val okHttpClient = OkHttpClient.Builder()
                .authenticator { _, response ->
                    response.request().newBuilder().addHeader("Authorization", "Bearer $GITHUB_ACCESS_TOKENS").build()
                }.build()
        val apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build()
        val query = UserQuery.builder().build()
        apolloClient.query(query).enqueue(object : ApolloCall.Callback<UserQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d(TAG, e.toString())
            }

            override fun onResponse(response: Response<UserQuery.Data>) {
                val user = response.data()?.user()
                Log.d(TAG, user?.login())
            }
        })
    }

    companion object {
        private const val BASE_URL = "https://api.github.com/graphql"
        private const val GITHUB_ACCESS_TOKENS = "dc80183faca1bea5ad47e6e180ca5f1a33abb829"
        private val TAG = MainViewModel::class.java.simpleName
    }
}
