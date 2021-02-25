package com.ikemura.android_kotlin_lab.permission.location

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.LocationFragmentBinding

/**
 * 位置情報PermissionをリクエストするFragment
 */
class LocationFragment : Fragment(R.layout.location_fragment) {
    private lateinit var binding: LocationFragmentBinding

    private val permissions =
        // Android 10から ACCESS_BACKGROUND_LOCATION が追加された
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        else
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )

    // 複数
    private val requestMultiPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            // 権限が許可されたか？
            Log.d(TAG, map.toString())
            checkLocationPermission()
        }

    // 単体
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                Log.d(TAG, "$result")
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LocationFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            // 位置情報Permissionのリクエスト
            fg.setOnClickListener {
                requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            bg.setOnClickListener {
                requestPermission.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
            all.setOnClickListener {
                requestMultiPermissions.launch(permissions)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkLocationPermission()
    }

    /**
     * 権限をチェックする
     */
    private fun checkLocationPermission() {
        permissions.forEach {
            val permissionResult = ContextCompat.checkSelfPermission(requireActivity(), it) == PackageManager.PERMISSION_GRANTED
            Log.d(TAG, "$it: $permissionResult")
        }
    }

    /**
     * 許可ダイアログの再表示判定（永続的に不許可設定の場合、falseが返却される）
     */
    private fun showRequestPermissionRationale() {
        permissions.forEach {
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), it)
        }
    }

    companion object {
        private val TAG = LocationFragment::class.java.simpleName
    }
}
