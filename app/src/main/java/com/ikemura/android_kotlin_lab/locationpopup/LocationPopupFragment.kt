package com.ikemura.android_kotlin_lab.locationpopup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.databinding.LocationPopupFragmentBinding
import com.ikemura.android_kotlin_lab.locationpopup.dialog.LocationPopupDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 位置情報の説明dialog　-> 位置情報permission要求 の表示Fragment
 */
class LocationPopupFragment : Fragment() {

    lateinit var binding: LocationPopupFragmentBinding
    private val viewModel: LocationPopupViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LocationPopupFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.locationPopupStartButton.setOnClickListener {
            startLocationPopup()
        }
    }

    private fun startLocationPopup() {
        LocationPopupDialogFragment().show(childFragmentManager, LocationPopupDialogFragment::class.java.name)
    }
}
