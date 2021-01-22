package com.ikemura.android_kotlin_lab.dialog.dialogfragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ikemura.android_kotlin_lab.R

/**
 * 背景が透明なDialogFragmentサンプル
 */
class TransportDialogFragment2 : DialogFragment(R.layout.transparent_dialog_fragment) {

    // ダイアログの黒背景を消して透明にする
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        setStyle(STYLE_NORMAL, R.style.TransportDialog) // スタイルで設定する場合

        return super.onCreateDialog(savedInstanceState).apply {
            // window?.setDimAmount(0f) // コードで暗い背景を消す
        }
    }
}
