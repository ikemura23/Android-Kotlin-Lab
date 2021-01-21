package com.ikemura.android_kotlin_lab.dialog.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.NormalDialogFragmentBinding

class NormalDialogFragment : DialogFragment(R.layout.normal_dialog_fragment) {

    private lateinit var binding: NormalDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.normal_dialog_fragment,
            container,
            false
        )
        return binding.root
    }
}
