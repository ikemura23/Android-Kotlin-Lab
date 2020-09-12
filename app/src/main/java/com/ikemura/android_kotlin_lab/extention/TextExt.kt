package com.ikemura.android_kotlin_lab.extention

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

/**
 * TextViewに下線付きで文字を表示する
 */
@BindingAdapter("textWithUnderLine")
fun TextView.textWithUnderLine(text: String?) {
    text ?: return
    this.text = HtmlCompat.fromHtml("<u>$text</u>", HtmlCompat.FROM_HTML_MODE_COMPACT)
}
