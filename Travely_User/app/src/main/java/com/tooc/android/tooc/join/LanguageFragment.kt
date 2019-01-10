package com.tooc.android.tooc.join

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tooc.android.tooc.R

class LanguageFragment : Fragment() {

    companion object {
        private const val CATEGORY = "category"

        fun newInstance(sectionCategory: String): LanguageFragment {
            val fragment = LanguageFragment()
            val args : Bundle? = Bundle()
            args?.putString(CATEGORY, sectionCategory)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_language, container, false)
        return v
    }
}