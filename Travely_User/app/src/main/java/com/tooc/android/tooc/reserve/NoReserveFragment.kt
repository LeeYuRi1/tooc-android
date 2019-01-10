package com.tooc.android.tooc.reserve

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tooc.android.tooc.R

class NoReserveFragment : Fragment() {

    companion object {
        var mInstance: NoReserveFragment? = null
        @Synchronized
        fun getInstance(): NoReserveFragment {
            if (mInstance == null) {
                mInstance = NoReserveFragment()
            }
            return mInstance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_no_reserve, container, false)
        return v
    }

}