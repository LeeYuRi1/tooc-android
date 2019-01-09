package com.hyeran.android.tooc.join

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.tooc.MainActivity
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.mypage.AccessTermsServiceFragment
import com.hyeran.android.tooc.mypage.SetFragment
import kotlinx.android.synthetic.main.fragment_explanation1.*
import org.jetbrains.anko.support.v4.ctx

class Explanation1Fragment : Fragment() {

    companion object {
        private const val CATEGORY = "category"

        fun newInstance(sectionCategory: String): Explanation1Fragment {
            val fragment = Explanation1Fragment()
            val args : Bundle? = Bundle()
            args?.putString(CATEGORY, sectionCategory)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_explanation1, container, false)
        return v
    }


}