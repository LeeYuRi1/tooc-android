package com.hyeran.android.tooc.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.tooc.MainActivity
import com.hyeran.android.tooc.R
import kotlinx.android.synthetic.main.fragment_access_terms.*
import org.jetbrains.anko.support.v4.ctx

class AccessTermsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_access_terms, container, false)
        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        iv_back_access_terms.setOnClickListener {
            (ctx as MainActivity).replaceFragment(SetFragment())
        }

        btn_service_access.setOnClickListener {
            (ctx as MainActivity).replaceFragment(AccessTermsServiceFragment())
        }

        btn_customer_access.setOnClickListener {
            (ctx as MainActivity).replaceFragment(AccessTermsCustomerFragment())
        }

        btn_host_access.setOnClickListener {
            (ctx as MainActivity).replaceFragment(AccessTermsHostFragment())
        }

    }
}