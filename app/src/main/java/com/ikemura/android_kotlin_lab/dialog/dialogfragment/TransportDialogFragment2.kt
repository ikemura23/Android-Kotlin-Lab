package com.ikemura.android_kotlin_lab.dialog.dialogfragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ikemura.android_kotlin_lab.R

/**
 * 背景が透明なDialogFragmentサンプル
 */
class TransportDialogFragment2 : DialogFragment(R.layout.transparent_dialog_fragment) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // スタイルで暗い背景を消す
        setStyle(STYLE_NORMAL, R.style.TransportDialog)

        return super.onCreateDialog(savedInstanceState).apply {
            // コードで暗い背景を消す https://stackoverflow.com/a/54166609
            // window?.setDimAmount(0f)
        }
    }
}
