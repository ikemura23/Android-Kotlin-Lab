package com.ikemura.android_kotlin_lab.extention

import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.backgroundColor
import androidx.core.text.color
import androidx.core.text.underline
import androidx.databinding.BindingAdapter
import com.ikemura.android_kotlin_lab.R

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
    }
}

/**
 * TextViewに文字色をつける（HtmlCompat 版）
 */
@BindingAdapter("textWithRedColor")
fun TextView.textWithRedColor(text: String?) {
    if (text.isNullOrEmpty()) return
    this.text = HtmlCompat.fromHtml("ここは<font color='red'>$text</font>", HtmlCompat.FROM_HTML_MODE_COMPACT)
}

/**
 * TextViewに文字色をつける（SpannableStringBuilder 版）
 */
@BindingAdapter("textWithRedColor2")
fun TextView.textWithRedColor2(text: String?) {
    if (text.isNullOrEmpty()) return
    val color = ContextCompat.getColor(this@textWithRedColor2.context, R.color.colorAccent)
    this.text = SpannableStringBuilder().color(color) { append(text) }
}

/**
 * 文字のBackgroundに色をつける（SpannableStringBuilder 版）
 */
@BindingAdapter("textWitBackgroundColor")
fun TextView.textWitBackgroundColor(text: String?) {
    if (text.isNullOrEmpty()) return
    val color = ContextCompat.getColor(this.context, R.color.colorAccent)
    this.text = SpannableStringBuilder().backgroundColor(color) {
        append(text)
    }
}
