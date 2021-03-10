package com.ikemura.android_kotlin_lab.dialog.dialogfragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ikemura.android_kotlin_lab.R

/**
 * カスタムアニメーションを使用したDialogFragmentのサンプル
 *
 */
class AnimationDialogFragment : DialogFragment(R.layout.normal_dialog_fragment) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ここでも使える
        // dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ここでも使える
        // dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        // ここでも使える
        // dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        super.onStart()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            // ここでも有効
            window?.attributes?.windowAnimations = R.style.DialogAnimation
        }
    }
}
