package com.ikemura.android_kotlin_lab.mocklocation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.DetectMockLocationFragmentBinding

class DetectMockLocationFragment : Fragment(R.layout.detect_mock_location_fragment) {

    private lateinit var binding: DetectMockLocationFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DetectMockLocationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DetectMockLocationFragmentBinding.inflate(layoutInflater)
        binding.check.setOnClickListener {
            checkMockLocation()
        }
    }

    /**
     * 疑似のGPSかチェックする
     */
    private fun checkMockLocation() {
        // TODO: チェック
    }

    private fun showResult(result: String) {
    }
}
