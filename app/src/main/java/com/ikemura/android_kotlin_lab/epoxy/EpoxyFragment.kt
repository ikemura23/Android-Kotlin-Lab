package com.ikemura.android_kotlin_lab.epoxy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.epoxy.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.epoxy_fragment.recycler_view

/**
 * Epoxy Fragment
 */
class EpoxyFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.epoxy_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = MainController()
        recycler_view.adapter = controller.adapter
        controller.requestModelBuild()
        // データを渡すとき
//        controller.setData(DummyItem("1", "2", "3"))
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
