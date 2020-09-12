package com.ikemura.android_kotlin_lab.extention

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.underline
import androidx.databinding.BindingAdapter

/**
 * TextViewに下線付きで文字を表示する（HtmlCompat 版）
 */
@BindingAdapter("textWithUnderLine")
fun TextView.textWithUnderLine(text: String?) {
    text ?: return
    this.text = HtmlCompat.fromHtml("<u>$text</u>", HtmlCompat.FROM_HTML_MODE_COMPACT)
}

/**
 * TextViewに下線付きで文字を表示する2 (SpannableStringBuilder 版）
 */
@BindingAdapter("textWithUnderLine2")
fun TextView.textWithUnderLine2(text: String?) {
    if (text.isNullOrEmpty()) return
    this.text = SpannableStringBuilder().underline {
        append(text)
        setSpan(UnderlineSpan(), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}
