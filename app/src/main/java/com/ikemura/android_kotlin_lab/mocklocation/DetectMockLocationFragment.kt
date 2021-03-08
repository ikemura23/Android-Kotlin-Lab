package com.ikemura.android_kotlin_lab.mocklocation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.DetectMockLocationFragmentBinding

/**
 * 疑似GPSを検出するFragment
 */
class DetectMockLocationFragment : Fragment(R.layout.detect_mock_location_fragment) {

    private lateinit var binding: DetectMockLocationFragmentBinding
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DetectMockLocationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.check.setOnClickListener {
            checkMockLocation()
        }
        // 位置情報リクエストを開始
        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    // アプリの位置情報開始権限がデバイスに許可されているか？
    private fun checkSelfPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }

    /**
     * 疑似のGPSかチェックする
     *
     * FusedLocationProviderClient を使うには play-services-location が必要
     * https://developers.google.com/android/guides/setup?hl=ja#dependencies
     *
     * 位置情報を取得するドキュメントは以下を参考にした
     * https://developer.android.com/training/location/retrieve-current?hl=ja#last-known
     */
    private fun checkMockLocation() {
        if (!checkSelfPermission()) return

        // 直近の位置情報を取得を開始
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            // location.isFromMockProvider == true だと偽の位置情報と判定してよい（実験済み）
            showResult(location.isFromMockProvider)
        }
    }

    /**
     * 結果表示
     */
    private fun showResult(result: Boolean) {
        val resultText = if (result) "GPS偽装を検出した" else "正常なGPSです"
        binding.result.text = resultText
    }
}
