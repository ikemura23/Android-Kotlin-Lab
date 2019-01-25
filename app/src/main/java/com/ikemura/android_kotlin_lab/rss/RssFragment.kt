package com.ikemura.android_kotlin_lab.rss

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ikemura.android_kotlin_lab.rss.dummy.DummyContent
import com.ikemura.android_kotlin_lab.rss.dummy.DummyContent.DummyItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.mcsoxford.rss.RSSReader

class RssFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.ikemura.android_kotlin_lab.R.layout.fragment_rss_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyRssRecyclerViewAdapter(DummyContent.ITEMS, listener)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchRss()
    }

    private fun fetchRss() {
        val url = "http://www.aneikankou.co.jp/news/index.xml"
        GlobalScope.launch {

            try {
                val reader = RSSReader()
                val uri = "http://www.aneikankou.co.jp/news/index.xml"
                val feed = reader.load(uri)
                feed.items.forEach { Log.d("RssFragment",it.toString()) }
            } catch (e: Exception) {
                Log.d("RssFragment", e.message)
                e.printStackTrace()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
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
        fun newInstance() = RssFragment()
    }
}
