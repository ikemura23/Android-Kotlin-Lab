package com.ikemura.android_kotlin_lab

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner

// bindingという引数を受け取ってUnitを返す変数をtypealiasで宣言している
typealias UseDataBindingBlock<Binding> = (binding: Binding) -> Unit

class UseBinding<Binding : ViewDataBinding>(
    val binding: Binding?
) {
    inline operator fun invoke(
        shouldExecutePendingBindings: Boolean = false,
        block: UseDataBindingBlock<Binding>
    ) {
        binding?.let {
            block(it)
            if (shouldExecutePendingBindings) {
                it.executePendingBindings()
            }
        }
    }
}

class UseBindingHelper<T : ViewDataBinding> {
    operator fun getValue(fragment: Fragment, prop: Any?): UseBinding<T> {
        val binding = fragment.view?.setUpBinding(fragment.viewLifecycleOwner)
        return UseBinding(binding)
    }

    operator fun getValue(fragmentActivity: FragmentActivity, prop: Any?): UseBinding<T> {
        val binding = (fragmentActivity.findViewById<View>(android.R.id.content) as? ViewGroup)
            ?.children
            ?.firstOrNull()
            ?.setUpBinding(fragmentActivity)
        return UseBinding(binding)
    }

    private fun View.setUpBinding(lifecycleOwner: LifecycleOwner): T? {
        return DataBindingUtil.bind<T>(this)?.also { it.lifecycleOwner = lifecycleOwner }
    }
}

fun <T : ViewDataBinding> bindings() = UseBindingHelper<T>()
