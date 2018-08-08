package com.ikemura.android_kotlin_lab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import kotlinx.android.synthetic.main.activity_main.text_view

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val html = """
            <html>
                <p>こんにちは、本日は<font color="#ff0000">晴天</font>なり
                <p>これが<u>アンダーライン</u>だ</p>
                <b><font color='red'>台風が</font><b/> 日本に <b><font color='red'>やってきた</font></b>
            </html>
        """.trimIndent()

        text_view.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
}
