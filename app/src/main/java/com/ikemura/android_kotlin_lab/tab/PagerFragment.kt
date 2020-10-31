package com.ikemura.android_kotlin_lab.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.PagerFragmentBinding
import com.ikemura.android_kotlin_lab.main.MainFragment

class PagerFragment : Fragment(R.layout.pager_fragment) {
    private lateinit var binding: PagerFragmentBinding
    private val pageItems = listOf(PagerItem.FirstItem, PagerItem.SecondItem, PagerItem.ThirdItem)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PagerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = pageItems.size

            override fun createFragment(position: Int): Fragment {
                return pageItems[position].newInstance()
            }
        }
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
    }

    private sealed class PagerItem {
        abstract fun newInstance(): Fragment

        object FirstItem : PagerItem() {
            override fun newInstance(): Fragment {
                return MainFragment.newInstance()
            }
        }

        object SecondItem : PagerItem() {
            override fun newInstance(): Fragment {
                return MainFragment.newInstance()
            }
        }

        object ThirdItem : PagerItem() {
            override fun newInstance(): Fragment {
                return MainFragment.newInstance()
            }
        }
    }
}
