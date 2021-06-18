package com.ikemura.android_kotlin_lab.dialog.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.NormalDialogFragmentBinding

/**
 * カスタムレイアウトを使用したDialogFragmentのサンプル
 *
 * 以下の書き方で、コストラクタにレイアウトidを渡すと、DataBindingを返さなくてもレイアウトは表示できる。
 * class NormalDialogFragment : DialogFragment(R.layout.normal_dialog_fragment) {
 */
class NormalDialogFragment : DialogFragment() {

    private lateinit var binding: NormalDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NormalDialogFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }
}
