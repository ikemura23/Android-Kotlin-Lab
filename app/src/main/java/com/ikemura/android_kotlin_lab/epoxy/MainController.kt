package com.ikemura.android_kotlin_lab.epoxy

import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.TypedEpoxyController
import com.ikemura.android_kotlin_lab.BodyViewBindingModel_
import com.ikemura.android_kotlin_lab.FooterViewBindingModel_
import com.ikemura.android_kotlin_lab.HeaderViewBindingModel_
import com.ikemura.android_kotlin_lab.epoxy.dummy.DummyContent

class MainController : TypedEpoxyController<DummyContent.DummyItem>() {
    @AutoModel
    lateinit var headerView: HeaderViewBindingModel_
    @AutoModel
    lateinit var bodyView: BodyViewBindingModel_
    @AutoModel
    lateinit var footerView: FooterViewBindingModel_

    override fun buildModels(item: DummyContent.DummyItem?) {
        item ?: return
//        headerView {
//            id("header")
//        }
//        bodyView {
//            id("body")
//        }
//        footerView {
//            id("footer")
//        }

        headerView.addTo(this)
        bodyView.addTo(this)
        footerView.addTo(this)
    }
}
