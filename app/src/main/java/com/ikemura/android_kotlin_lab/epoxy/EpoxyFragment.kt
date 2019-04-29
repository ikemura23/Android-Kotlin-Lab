package com.ikemura.android_kotlin_lab.epoxy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.epoxy.dummy.DummyContent
import com.ikemura.android_kotlin_lab.epoxy.dummy.DummyContent.DummyItem

/**
 * Epoxy Fragment
 */
class EpoxyFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.epoxy_fragment, container, false)
        if (view is RecyclerView) {
            with(view) {
                adapter = EpoxyListRecyclerViewAdapter(DummyContent.ITEMS, listener)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: DummyItem?)
    }

    companion object {
        @JvmStatic
        fun newInstance() = EpoxyFragment()
    }
}
