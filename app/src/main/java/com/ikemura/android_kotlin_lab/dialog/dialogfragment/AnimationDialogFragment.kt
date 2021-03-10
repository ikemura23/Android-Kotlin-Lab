package com.ikemura.android_kotlin_lab.dialog.dialogfragment

import androidx.fragment.app.DialogFragment
import com.ikemura.android_kotlin_lab.R

/**
 * カスタムレイアウトを使用したDialogFragmentのサンプル
 *
 * 以下の書き方で、コストラクタにレイアウトidを渡すと、DataBindingを返さなくてもレイアウトは表示できる。
 * class NormalDialogFragment : DialogFragment(R.layout.normal_dialog_fragment) {
 */
class AnimationDialogFragment : DialogFragment(R.layout.normal_dialog_fragment) {

}
