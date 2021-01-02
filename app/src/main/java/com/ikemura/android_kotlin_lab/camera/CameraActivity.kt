package com.ikemura.android_kotlin_lab.camera

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ikemura.android_kotlin_lab.R

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CameraFragment.newInstance())
            .commitNow()
    }

    override fun onResume() {
        super.onResume()

        // フルスクリーンフラグを設定する前に、UIが落ち着くまで少し待つ必要があります。
        // そうしないと、準備が整う前にアプリをイマーシブモードに設定しようとしていて、フラグが固定されない可能性があります
        // https://github.com/android/camera-samples/blob/main/CameraXBasic/app/src/main/java/com/android/example/cameraxbasic/MainActivity.kt#L48
        findViewById<View>(R.id.container).run {
            postDelayed(
                { systemUiVisibility = FLAGS_FULLSCREEN },
                IMMERSIVE_FLAG_TIMEOUT
            )
        }
    }

    companion object {
        // アクティビティを没入型モードにするために必要なすべてのフラグの組み合わせ
        // https://github.com/android/camera-samples/blob/main/CameraXBasic/app/src/main/java/com/android/example/cameraxbasic/utils/ViewExtensions.kt#L27
        const val FLAGS_FULLSCREEN =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        private const val IMMERSIVE_FLAG_TIMEOUT = 500L
    }
}
