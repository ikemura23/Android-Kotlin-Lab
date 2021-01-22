package com.ikemura.android_kotlin_lab.dialog.dialogfragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * アラートダイアログのサンプル
 */
class AlertDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage("メッセージ")
            .setPositiveButton("OK") { _, _ -> clickPositive() }
            .setNegativeButton("Cancel") { _, _ -> clickNegative() }
            .create()
    }

    private fun clickPositive() {
        // なにかの処理
        dismiss()
    }

    private fun clickNegative() {
        // なにかの処理
        dismiss()
    }
}
