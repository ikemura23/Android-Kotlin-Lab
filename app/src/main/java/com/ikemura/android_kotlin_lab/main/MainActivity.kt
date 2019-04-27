package com.ikemura.android_kotlin_lab.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.epoxy.EpoxyFragment
import com.ikemura.android_kotlin_lab.epoxy.dummy.DummyContent

class MainActivity : AppCompatActivity(), EpoxyFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFragment()
    }

    private fun setupFragment() {
        supportFragmentManager.commit(false) {
            replace(R.id.container, EpoxyFragment.newInstance())
        }
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        Toast.makeText(this, "$item click", Toast.LENGTH_SHORT).show()
    }
}
