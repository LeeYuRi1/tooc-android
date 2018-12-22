package com.hyeran.android.travely_user


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class OneFragment : Fragment() {

    companion object {
        var mInstance: OneFragment? = null

        @Synchronized
        fun getInstance(): OneFragment {
            if (mInstance == null) {
                mInstance = OneFragment()
            }
            return mInstance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_one, container, false)

        return v
    }


}
