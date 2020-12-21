package com.ikemura.android_kotlin_lab.sub

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.bindings
import com.ikemura.android_kotlin_lab.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity(R.layout.activity_sub) {
    private val useBinding by bindings<ActivitySubBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        useBinding {
            it.button.setOnClickListener {
                setResult(RESULT_OK, Intent().apply {
                    putExtra("RESULT", "SubActivity result 999")
                })
                finish()
            }
        }
    }

    class SubActivityResultContract : ActivityResultContract<Unit, String?>() {
        override fun createIntent(context: Context, input: Unit?): Intent {
            return Intent(context, SubActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            return when {
                resultCode != Activity.RESULT_OK -> null
                else -> intent?.getStringExtra("RESULT")
            }
        }
    }
}
