package com.tooc.android.tooc.ship


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tooc.android.tooc.R

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
        return inflater.inflate(R.layout.fragment_ship, container, false)
    }
}
