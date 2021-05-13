package com.ikemura.android_kotlin_lab.speech

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.R

class SpeechRecognizerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_speech_recognizer, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) = SpeechRecognizerFragment()
    }
}
