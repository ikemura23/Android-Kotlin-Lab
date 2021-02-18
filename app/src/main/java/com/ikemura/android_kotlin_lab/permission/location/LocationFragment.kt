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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        else
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            // 権限が許可されたか？
            Log.d(TAG, map.toString())
            showRequestPermissionRationale()
            checkLocationPermission()
        }
    private val askPermissions =
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

        // 位置情報Permissionのリクエスト
        binding.ACCESSFINELOCATION.setOnClickListener {
            // requestPermissionLauncher.launch(permissions)
            askPermissions.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        binding.ACCESSBACKGROUNDLOCATION.setOnClickListener {
            // askPermissions.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION) // <= これはうごかない
            requestPermissionLauncher.launch(permissions)
        }
        binding.permissionRationale.setOnClickListener {
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
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

    private fun showRequestPermissionRationale() {
        permissions.forEach {
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), it)
        }
    }

    companion object {
        private val TAG = LocationFragment::class.java.simpleName
    }
}