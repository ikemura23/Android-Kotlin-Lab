package com.ikemura.android_kotlin_lab.locationpopup

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.databinding.LocationPopupFragmentBinding
import com.ikemura.android_kotlin_lab.locationpopup.dialog.LocationPopupDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 位置情報の説明dialog　-> 位置情報permission要求 の表示Fragment
 */
class LocationPopupFragment : Fragment() {

    // 単体
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                Log.d(TAG, "RequestPermission: $result")
            }
        }

    lateinit var binding: LocationPopupFragmentBinding
    private val viewModel: LocationPopupViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LocationPopupFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startLocationPopup()
        // binding.locationPopupStartButton.setOnClickListener {
        //     startLocationPopup()
        // }
    }

    /**
     * メイン処理スタート
     */
    private fun startLocationPopup() {
        LocationPopupDialogFragment().show(childFragmentManager, LocationPopupDialogFragment::class.java.name)
        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    companion object {
        private val TAG = LocationPopupFragment::class.java.name
    }
}
