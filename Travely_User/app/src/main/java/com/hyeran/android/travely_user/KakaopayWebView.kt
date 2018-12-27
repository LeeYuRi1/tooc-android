package com.hyeran.android.travely_user

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import kotlinx.android.synthetic.main.activity_kakaopay_web_view.*
import kotlinx.android.synthetic.main.dialog_writereview.*
import org.jetbrains.anko.ctx

class KakaopayWebView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakaopay_web_view)

        iv_back_kakaopay_web_view.setOnClickListener {
            finish()
        }
        iv_x_kakaopay_web_view.setOnClickListener {
            finish()
        }

        tv_result_kakaopay_web_view.text = "성공"

        tv_result_kakaopay_web_view.text = "실패"

        btn_ok_kakaopay_web_view.setOnClickListener {
            finish()
        }

//        btn_ok_kakaopay_web_view.setOnClickListener {
//            finish()
//        }
    }

    fun getResult() {

    }

}
