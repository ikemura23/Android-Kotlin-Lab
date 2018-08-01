package com.ikemura.android_kotlin_lab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_second.second_navigate_button

class SecondFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val args = SecondFragmentArgs.fromBundle(arguments)
//        Log.d("SecondFragment", args.amount.toString())

        val amount = arguments?.getInt("amount")
        Log.d("SecondFragment", amount.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ボタン押下イベント
        second_navigate_button.setOnClickListener {
            //ナビゲーション開始
            Navigation.findNavController(it).navigate(
                    SecondFragmentDirections
                            .actionSecondFragmentToItemFragment("100")
            )
        }

    }
}
