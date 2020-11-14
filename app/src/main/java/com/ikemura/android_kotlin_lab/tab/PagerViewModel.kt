package com.ikemura.android_kotlin_lab.tab

import androidx.lifecycle.ViewModel
import com.ikemura.android_kotlin_lab.repository.DummyRepository

class PagerViewModel(
    private val dummyRepository: DummyRepository
) : ViewModel() {
}
