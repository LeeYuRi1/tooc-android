package com.tooc.android.tooc.join

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tooc.android.tooc.R

class Explanation2Fragment : Fragment() {

    companion object {
        private const val CATEGORY = "category"

        fun newInstance(sectionCategory: String): Explanation2Fragment {
            val fragment = Explanation2Fragment()
            val args : Bundle? = Bundle()
            args?.putString(CATEGORY, sectionCategory)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_explanation2, container, false)
        return v
    }

}