package com.ikemura.android_kotlin_lab.permission.location

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.LocationFragmentBinding
import com.ikemura.android_kotlin_lab.extention.viewBinding

/**
 * 位置情報PermissionをリクエストするFragment
 */
class LocationFragment : Fragment(R.layout.location_fragment) {
    private val binding: LocationFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: 位置情報Permissionのリクエスト
    }
}
