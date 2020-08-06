package com.ikemura.android_kotlin_lab

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 *
 * https://proandroiddev.com/livedata-with-single-events-2395dea972a8
 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? = if (hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        content
    }

    fun peekContent(): T = content
}

fun <T : Any> T.toEvent(): Event<T> = Event(this)
