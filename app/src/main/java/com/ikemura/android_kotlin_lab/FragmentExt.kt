package com.ikemura.android_kotlin_lab

import androidx.databinding.ViewDataBinding

// bindingという引数を受け取ってUnitを返す変数をtypealiasで宣言している
typealias UserDataBindingBlock<Binding> = (binding: Binding) -> Unit

class UserBinding<Binding : ViewDataBinding>(
    val binding: Binding?
) {

}
