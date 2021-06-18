package com.ikemura.android_kotlin_lab.locationpopup.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.databinding.LocationPupupDialogFragmentBinding

class LocationPopupDialogFragment : DialogFragment() {

    lateinit var binding: LocationPupupDialogFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LocationPupupDialogFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            // cancel
        }
        binding.okButton.setOnClickListener {
            // OK
        }
    }

    private fun startLocationPopup() {
        // TODO メイン処理
    }
}
