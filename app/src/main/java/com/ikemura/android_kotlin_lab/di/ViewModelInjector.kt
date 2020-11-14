package com.ikemura.android_kotlin_lab.di

import com.ikemura.android_kotlin_lab.dialog.datepickr.dialogfragment.DialogRootViewModel
import com.ikemura.android_kotlin_lab.repository.DummyRepository

object ViewModelInjector {
    fun dialogRootViewModel() = DialogRootViewModel(
        dummyRepository = DummyRepository()
    )
}
