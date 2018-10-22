package com.ikemura.android_kotlin_lab

class InputChecker {
    fun isValid(text: String?): Boolean {
        if (text == null) return false
        return text.length >= 3 && text.matches(Regex("[a-zA-Z0-9]+"))
    }
}