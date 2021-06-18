package com.ikemura.android_kotlin_lab.locationpopup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.databinding.LocationPopupFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

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
            startLocationPoopup()
        }
    }

    private fun startLocationPoopup() {
        // TODO メイン処理
    }
}
