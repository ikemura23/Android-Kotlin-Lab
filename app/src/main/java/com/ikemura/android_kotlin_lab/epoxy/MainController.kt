package com.ikemura.android_kotlin_lab.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.ikemura.android_kotlin_lab.epoxy.dummy.DummyContent
import com.ikemura.android_kotlin_lab.epoxyListItem
import com.ikemura.android_kotlin_lab.headerView

class MainController : TypedEpoxyController<DummyContent.DummyItem>() {
    override fun buildModels(item: DummyContent.DummyItem?) {
        item ?: return
        headerView {
            id("title")
            title("Hello Epoxy!")
        }
        epoxyListItem {

        }
    }
}
