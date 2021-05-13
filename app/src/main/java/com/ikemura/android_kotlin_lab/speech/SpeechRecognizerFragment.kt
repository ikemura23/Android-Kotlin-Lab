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
import com.ikemura.android_kotlin_lab.databinding.FragmentSpeechRecognizerBinding

class SpeechRecognizerFragment : Fragment() {

    private lateinit var binding: FragmentSpeechRecognizerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSpeechRecognizerBinding.inflate(inflater)
        // 音声処理
        setupSpeechRecognizer()
        return binding.root
    }

    private fun setupSpeechRecognizer() {
        val recognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
        val listener = createRecognitionListener()
        recognizer.setRecognitionListener(listener)
        val intent = createRecognizerIntent()

        binding.startButton.setOnClickListener { recognizer.startListening(intent) }
        binding.endButton.setOnClickListener { recognizer.stopListening() }
    }

    private fun createRecognitionListener(): RecognitionListener {
        return object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.d(TAG, "onReadyForSpeech: $params")
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
                results?.let {
                    val list = it.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    Log.d(TAG, "onResults: $list")
                }
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
        // https://developer.android.com/reference/android/speech/RecognizerIntent
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    }

    companion object {
        private const val TAG = "SpeechRecognizerFragmen"

        @JvmStatic
        fun newInstance(param1: String, param2: String) = SpeechRecognizerFragment()
    }
}
