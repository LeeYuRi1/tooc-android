package com.hyeran.android.travely_manager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_mypage.view.*

class MypageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_mypage, container, false)

        v.btn_review_mypage.setOnClickListener {
            val manager = activity!!.supportFragmentManager
            val transaction : FragmentTransaction = manager.beginTransaction()
            transaction.replace(R.id.frame_main, ReviewFragment())
            transaction.commit()
        }

        return v
    }
}
