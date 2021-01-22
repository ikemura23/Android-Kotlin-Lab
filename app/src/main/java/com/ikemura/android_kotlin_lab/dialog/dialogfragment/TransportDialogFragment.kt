package com.ikemura.android_kotlin_lab.dialog.dialogfragment

import androidx.fragment.app.DialogFragment
import com.ikemura.android_kotlin_lab.R

/**
 * 背景が透明なDialogFragmentサンプル
 */
class TransportDialogFragment : DialogFragment(R.layout.normal_dialog_fragment) {

    // ダイアログの黒背景を消して透明にする
    override fun onStart() {
        super.onStart()
        dialog?.window?.also { window ->
            window.attributes?.also { attr ->
                attr.dimAmount = 0f
                window.attributes = attr
            }
        }
    }
}
