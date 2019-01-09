package com.hyeran.android.tooc.join

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.tooc.MainActivity
import com.hyeran.android.tooc.R
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {
    companion object {
        private const val CATEGORY = "category"

        fun newInstance(sectionCategory: String): WelcomeFragment {
            val fragment = WelcomeFragment()
            val args: Bundle? = Bundle()
            args?.putString(CATEGORY, sectionCategory)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_welcome, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_start_welcome.setOnClickListener {
            val intent = Intent(this.activity, MainActivity::class.java)
            startActivity(intent)
        }

    }
}