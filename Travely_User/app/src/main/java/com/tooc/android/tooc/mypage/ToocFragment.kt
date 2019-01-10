package com.tooc.android.tooc.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tooc.android.tooc.MainActivity
import com.tooc.android.tooc.R
import kotlinx.android.synthetic.main.fragment_tooc.*
import org.jetbrains.anko.support.v4.ctx

class ToocFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_tooc, container, false)
        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        iv_back_tooc.setOnClickListener {
            (ctx as MainActivity).replaceFragment(SetFragment())
        }
    }
}