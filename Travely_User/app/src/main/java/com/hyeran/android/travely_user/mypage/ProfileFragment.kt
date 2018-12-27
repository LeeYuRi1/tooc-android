package com.hyeran.android.travely_user.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.travely_user.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        iv_modification_profile.setOnClickListener {
            iv_modification_profile.visibility = View.GONE
            tv_confilm_profile.visibility = View.VISIBLE
            et_name_profile.isFocusableInTouchMode = true
            et_email_profile.isFocusableInTouchMode = true
            et_password_profile.isFocusableInTouchMode = true
            et_password_confirm_profile.isFocusableInTouchMode = true
        }

        tv_confilm_profile.setOnClickListener {

            et_name_profile.setText(et_name_profile.text)

            iv_modification_profile.visibility = View.VISIBLE
            tv_confilm_profile.visibility = View.GONE
            et_name_profile.isFocusableInTouchMode = false
            et_email_profile.isFocusableInTouchMode = false
            et_password_profile.isFocusableInTouchMode = false
            et_password_confirm_profile.isFocusableInTouchMode = false

        }
    }

}