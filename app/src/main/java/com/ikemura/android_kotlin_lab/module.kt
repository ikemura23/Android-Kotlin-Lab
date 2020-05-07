package com.ikemura.android_kotlin_lab

import com.ikemura.android_kotlin_lab.repository.ISampleRepository
import com.ikemura.android_kotlin_lab.repository.SampleRepository
import org.koin.dsl.module

val appModule = module {
    single<ISampleRepository> { SampleRepository() }
}
