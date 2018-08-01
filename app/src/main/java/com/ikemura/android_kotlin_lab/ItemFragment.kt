package com.ikemura.android_kotlin_lab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ikemura.android_kotlin_lab.dummy.DummyContent
import com.ikemura.android_kotlin_lab.dummy.DummyContent.DummyItem

class ItemFragment : Fragment(), OnListFragmentInteractionListener {

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = this
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS, listener)
            }
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onListFragmentInteraction(item: DummyItem?) {
        Toast.makeText(context, item.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                ItemFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}

interface OnListFragmentInteractionListener {
    fun onListFragmentInteraction(item: DummyItem?)
}