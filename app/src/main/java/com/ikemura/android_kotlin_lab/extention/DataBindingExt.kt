package com.ikemura.android_kotlin_lab.extention

import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewDataBinding> AppCompatActivity.viewBinding(): ReadOnlyProperty<AppCompatActivity, T> {
    return object : ReadOnlyProperty<AppCompatActivity, T> {
        override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
            val view = thisRef.findViewById<ViewGroup>(android.R.id.content)[0]
            val binding = DataBindingUtil.bind<T>(view)!!
            binding.lifecycleOwner = thisRef
            return binding
        }
    }
}

fun <T : ViewDataBinding> Fragment.viewBinding(): ReadOnlyProperty<Fragment, T> {
    return object : ReadOnlyProperty<Fragment, T> {
        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            val view = thisRef.view ?: throw FragmentViewNotFoundException()
            val binding = DataBindingUtil.bind<T>(view)!!
            binding.lifecycleOwner = thisRef.viewLifecycleOwner
            return binding
        }
    }
}
class FragmentViewNotFoundException : Exception("Fragment#getView() returned null.")

// コールバックや遅延実行等でonDestroyView()後にBindingを触る可能性がある場合に利用
inline fun Fragment.safeViewBinding(block: () -> Unit) {
    try {
        block.invoke()
    } catch (e: FragmentViewNotFoundException) {
        Log.w(this::class.java.simpleName, e.message!!)
    }
}
