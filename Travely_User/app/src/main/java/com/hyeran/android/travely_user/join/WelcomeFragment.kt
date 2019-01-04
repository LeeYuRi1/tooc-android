package com.hyeran.android.travely_user.join

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.travely_user.R

class WelcomeFragment : Fragment() {
    companion object {
        private const val CATEGORY = "category"

        fun newInstance(sectionCategory: String): WelcomeFragment {
            val fragment = WelcomeFragment()
            val args : Bundle? = Bundle()
            args?.putString(CATEGORY, sectionCategory)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_welcome, container, false)
        return v
    }
}