package com.ikemura.android_kotlin_lab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_first.fist_navigate_button

class FirstFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)

//        遷移方法1
//        val view = inflater.inflate(R.layout.fragment_first, container, false)
//        view.findViewById<Button>(R.id.fist_navigate_button).setOnClickListener {
//            Navigation.findNavController(it).navigate(R.id.secondFragment)
//        }
//        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //遷移方法2
        val bundle = Bundle().apply { putInt("amount", 2) }
        fist_navigate_button.setOnClickListener(
                //ナビゲーション開始
                Navigation.createNavigateOnClickListener(R.id.secondFragment, bundle)
        )

//        //遷移方法3
//        fist_navigate_button.setOnClickListener {
//            Navigation.findNavController(it).navigate(
//                    FirstFragmentDirections
//                            .actionFirstFragmentToSecondFragment()
//                            .setAmount(1)
//            )
//        }
    }
}
