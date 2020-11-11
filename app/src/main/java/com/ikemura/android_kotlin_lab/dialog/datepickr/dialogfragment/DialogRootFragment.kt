package com.ikemura.android_kotlin_lab.dialog.datepickr.dialogfragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.bindings
import com.ikemura.android_kotlin_lab.databinding.FragmentDialogRootBinding
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * ダイアログ系を表示するルート画面
 */
class DialogRootFragment : Fragment() {
    private val useBinding by bindings<FragmentDialogRootBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_root, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        useBinding {
            it.datePicker.setOnClickListener {
                showDatePicker()
            }
            it.oldDatePicker.setOnClickListener {
                showOldDatePicker()
            }
        }
    }

    /**
     * Date Picker表示
     */
    private fun showDatePicker() {
        MaterialDatePicker.Builder.datePicker().build().apply {
            // OKクリック
            addOnPositiveButtonClickListener { datetime ->
                useBinding {
                    it.dateText.text = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN).format(datetime)
                }
            }
        }.show(childFragmentManager, "DatePicker")
    }

    private fun showOldDatePicker() {
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                useBinding {
                    it.dateText.text = "$year/$month/$dayOfMonth"
                }
            },
            2012, // 仮の値
            11, // 仮の値
            5 // 仮の値
        ).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = DialogRootFragment()
    }
}
