package com.hyeran.android.travely_user.join

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.hyeran.android.travely_user.R
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_login.*

class JoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        setClickListener()
        checkEditText()

    }

    private fun setClickListener() {
        btn_confirm_join.setOnClickListener {
            val intent = Intent(applicationContext, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkEditText() {

        et_name_join.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (et_name_join.text.toString().length != 0) {

                } else if (et_name_join.text.toString().length == 0) {

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        et_email_join.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (et_email_join.text.toString().length != 0) {
                    iv_email_check_join.visibility = View.VISIBLE
                } else if (et_email_join.text.toString().length == 0) {
                    iv_email_check_join.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        et_password_join.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (et_password_join.text.toString().length != 0) {
                    iv_password_check_join.visibility = View.VISIBLE
                } else if (et_password_join.text.toString().length == 0) {
                    iv_password_check_join.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        et_password_confilm_join.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (et_password_confilm_join.text.toString().length != 0) {
                    if (et_password_join.text.toString() == et_password_confilm_join.text.toString()) {
                        iv_passwordconfirm_check_join.visibility = View.VISIBLE
                        iv_passwordconfirm_x_join.visibility = View.GONE
                    } else {
                        iv_passwordconfirm_check_join.visibility = View.GONE
                        iv_passwordconfirm_x_join.visibility = View.VISIBLE
                    }
                } else if (et_password_confilm_join.text.toString().length == 0) {
                    iv_passwordconfirm_check_join.visibility = View.GONE
                    iv_passwordconfirm_x_join.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

//        if(et_name_join.text.toString().length != 0
//                && et_email_join.text.toString().length != 0
//                && et_password_join.text.toString().length != 0
//                && et_password_confilm_join.text.toString().length != 0){
//            btn_confirm_join.visibility = View.GONE
//            btn_confirm_fill_join.visibility = View.VISIBLE
//        }

    }


}
