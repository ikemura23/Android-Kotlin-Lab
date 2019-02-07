package com.ikemura.android_kotlin_lab.main

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.R
import kotlinx.android.synthetic.main.fragment_sample.main_text

class SampleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpannable()
        createBundle()
    }

    private fun createBundle() {
        val bundle = Bundle().apply {
            putInt("1", 1)
        }
        Log.d("SampleFragment", bundle.toString())

        val bundle2 = bundleOf(
            Pair("1", 1),
            Pair("2", "222")
        )
        Log.d("SampleFragment", bundle2.toString())
    }

    private fun setSpannable() {
        // 0〜5文字の文字サイズ100spにする
        val sp1 = "0123456789".toSpannable()
        sp1[0..5] = AbsoluteSizeSpan(100)
        main_text.text = sp1

        // 3〜5文字をリンク
        sp1[3..5] = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Log.d("tag", "click")
                Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show()
            }
        }
        main_text.movementMethod = LinkMovementMethod.getInstance()
        main_text.text = sp1
    }
}
