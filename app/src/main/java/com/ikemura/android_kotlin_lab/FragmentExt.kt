package com.ikemura.android_kotlin_lab

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

// bindingという引数を受け取ってUnitを返す変数をtypealiasで宣言している
typealias UserDataBindingBlock<Binding> = (binding: Binding) -> Unit

class UserBinding<Binding : ViewDataBinding>(
    val binding: Binding?
) {
    inline operator fun invoke(
        shouldExecutePendingBindings: Boolean = false,
        block: UserDataBindingBlock<Binding>
    ) {
        // bindingがnullなら実行されない
        binding?.let {
            block(it)
            if (shouldExecutePendingBindings) {
                it.executePendingBindings()
            }
        }
    }
}

class UserBindingHelper<T : ViewDataBinding> {
    operator fun getValue(fragment: Fragment, prop: Any?): UserBinding<T> {
        val binding = fragment.view?.setUpBinding(fragment.viewLifecycleOwner)
        return UserBinding(binding)
    }

    private fun View.setUpBinding(lifecycleOwner: LifecycleOwner): T? {
        return DataBindingUtil.bind<T>(this)?.also { it.lifecycleOwner = lifecycleOwner }
    }
}

fun <T : ViewDataBinding> bindings() = UserBindingHelper<T>()
