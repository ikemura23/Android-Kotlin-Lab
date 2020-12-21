package com.ikemura.android_kotlin_lab.sub

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.ikemura.android_kotlin_lab.R

class SubActivity : AppCompatActivity(R.layout.activity_sub) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    class SubActivityResultContract : ActivityResultContract<Unit, String?>() {
        override fun createIntent(context: Context, input: Unit?): Intent {
            return createIntent(context, input)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            return when {
                resultCode != Activity.RESULT_OK -> null
                else -> intent?.getStringExtra("data")
            }
        }
    }
}
