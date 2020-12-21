package com.ikemura.android_kotlin_lab

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(MyObserver())
    }
}
