package com.hyeran.android.travely_manager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ShipFragment : Fragment() {

    companion object {
        var mInstance: ShipFragment? = null
        @Synchronized
        fun getInstance(): ShipFragment {
            if (mInstance == null) {
                mInstance = ShipFragment()
            }
            return mInstance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_ship, container, false)
        return v
    }
}
