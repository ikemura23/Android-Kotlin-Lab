package com.ikemura.android_kotlin_lab.navigation

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NavigationEventLiveData<T> : MutableLiveData<T>() where T : NavigationEvent {

    private var busy: Boolean = false

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer { navigationEvent ->
            navigationEvent?.let {
                if (busy) return@let
                GlobalScope.launch {
                    runCatching {
                        withContext(Dispatchers.Main) {
                            busy = true
                            observer.onChanged(navigationEvent)
                            value = null
                            delay(500L)
                            busy = false
                        }
                    }
                }
            }
        })
    }
}
