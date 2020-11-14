package com.ikemura.android_kotlin_lab.dialog.datepickr.dialogfragment

import androidx.lifecycle.ViewModel
import com.ikemura.android_kotlin_lab.repository.DummyRepository

class DialogRootViewModel(
    private val dummyRepository: DummyRepository
) : ViewModel() {
    fun getPageData() = dummyRepository.getDummy()
}
