package com.ikemura.android_kotlin_lab.common.extention

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.ikemura.android_kotlin_lab.common.Event

fun <T> LiveData<T>.observeNonNull(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
    observe(lifecycleOwner, Observer {
        if (it != null) {
            block(it)
        }
    })
}

fun <T> LiveData<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
    observe(lifecycleOwner, Observer { ev ->
        ev?.getContentIfNotHandled()?.let(block)
    })
}
