package com.ikemura.android_kotlin_lab.dialog.dialogfragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AlertDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage("メッセージ")
            .setPositiveButton("OK") { _, _ -> clickPositive() }
            .setNegativeButton("Cancel") { _, _ -> clickNegative() }
            .create()
    }

    private fun clickPositive() {
        // なにの処理
        dismiss()
    }

    private fun clickNegative() {
        // なにの処理
        dismiss()
    }
}
