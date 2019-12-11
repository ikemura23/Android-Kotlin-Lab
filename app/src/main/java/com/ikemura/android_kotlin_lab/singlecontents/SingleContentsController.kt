package com.ikemura.android_kotlin_lab.singlecontents

import com.airbnb.epoxy.Typed2EpoxyController
import com.ikemura.android_kotlin_lab.Data
import com.ikemura.android_kotlin_lab.headerView
import com.ikemura.android_kotlin_lab.simpleContentView

class SingleContentsController : Typed2EpoxyController<String, Data>() {
    override fun buildModels(title: String, body: Data) {
        headerView {
            id("header")
            title(title)
        }
        simpleContentView {
            id("body")
            data(body)
        }
    }

    // override fun buildModels(title, body: Data) {
    //     simpleContentView {
    //         id("body1")
    //         data(_data)
    //     }
    // }

}
