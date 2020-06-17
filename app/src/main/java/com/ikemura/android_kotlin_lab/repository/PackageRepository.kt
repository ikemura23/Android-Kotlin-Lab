package com.ikemura.android_kotlin_lab.repository

import android.content.Context
import android.content.pm.PackageManager

class PackageRepository(private val context: Context) {

    /**
     * 端末内のパッケージ名を取得する
     * https://developer.android.com/reference/android/content/pm/PackageManager#getInstalledPackages(int)
     */
    fun load(flag: Int = PackageManager.GET_ACTIVITIES): List<String> {
        val packages = context.packageManager.getInstalledPackages(flag)
        return packages.map { it.packageName }
    }
}
