package com.ikemura.android_kotlin_lab.speech

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.R
import java.util.Locale

class SpeechRecognizerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // 音声処理
        setupSpeechRecognizer()
        return inflater.inflate(R.layout.fragment_speech_recognizer, container, false)
    }

    private fun setupSpeechRecognizer() {
        val recognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
        val listener = createRecognitionListener()
        recognizer.setRecognitionListener(listener)
        val intent = createRecognizerIntent()

        recognizer.startListening(intent)
    }

    private fun createRecognitionListener(): RecognitionListener {
        return object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.d(TAG, "onReadyForSpeech")
            }

            override fun onBeginningOfSpeech() {
                Log.d(TAG, "onBeginningOfSpeech")
            }

            override fun onRmsChanged(rmsdB: Float) {
                Log.d(TAG, "onRmsChanged")
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                Log.d(TAG, "onBufferReceived")
            }

            override fun onEndOfSpeech() {
                Log.d(TAG, "onEndOfSpeech")
            }

            override fun onError(error: Int) {
                Log.d(TAG, "onError")
            }

            override fun onResults(results: Bundle?) {
                Log.d(TAG, "onResults")
            }

            override fun onPartialResults(partialResults: Bundle?) {
                Log.d(TAG, "onPartialResults")
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.d(TAG, "onEvent")
            }
        }
    }

    private fun createRecognizerIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).also {
            it.putExtra(RecognizerIntent.LANGUAGE_MODEL_FREE_FORM, true)
            // 音声がなければ5秒待って閉じる : 5000
            it.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 5000)
            // 音声入力停止判定を3秒 : 3000
            it.putExtra(
                RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,
                3000
            )
            it.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 50000)
            it.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            it.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.JAPAN.toString())
            it.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
    }

    companion object {
        private const val TAG = "SpeechRecognizerFragmen"

        @JvmStatic
        fun newInstance(param1: String, param2: String) = SpeechRecognizerFragment()
    }
}
