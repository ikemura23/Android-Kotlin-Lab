package com.ikemura.android_kotlin_lab.sub

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.bindings
import com.ikemura.android_kotlin_lab.databinding.ActivitySubBinding
import kotlinx.android.parcel.Parcelize

class SubActivity : AppCompatActivity(R.layout.activity_sub) {
    private val useBinding by bindings<ActivitySubBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        useBinding {
            // it.button.setOnClickListener {
            //     setResult(RESULT_OK, Intent().apply {
            //         putExtra("RESULT", "SubActivity result 999")
            //     })
            //     finish()
            // }
            it.button2.setOnClickListener {
                val type = intent.getSerializableExtra("myInputKey") as SubActivitySealedResultContract.InputMyType
                val result = when (type) {
                    SubActivitySealedResultContract.InputMyType.Type1 -> SubActivitySealedResultContract.Out.hoge
                    SubActivitySealedResultContract.InputMyType.Type2 -> SubActivitySealedResultContract.Out.fuga("fuga fuga")
                }
                setResult(RESULT_OK, Intent().apply {
                    putExtra("RESULT2", result)
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

    class SubActivitySealedResultContract :
        ActivityResultContract<SubActivitySealedResultContract.InputMyType, SubActivitySealedResultContract.Out>() {
        override fun createIntent(context: Context, input: InputMyType): Intent {
            return Intent(context, SubActivity::class.java).apply {
                putExtra("myInputKey", input)
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Out? {
            return when {
                resultCode != Activity.RESULT_OK -> null
                else -> intent?.getParcelableExtra("RESULT2")
            }
        }

        enum class InputMyType {
            Type1,
            Type2;
        }

        sealed class Out : Parcelable {
            @Parcelize
            object hoge : Out()

            @Parcelize
            data class fuga(val str: String) : Out()
        }
    }
}
