package com.ikemura.android_kotlin_lab.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.PagerFragmentBinding

class PagerFragment : Fragment(R.layout.pager_fragment) {
    private lateinit var binding: PagerFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PagerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
