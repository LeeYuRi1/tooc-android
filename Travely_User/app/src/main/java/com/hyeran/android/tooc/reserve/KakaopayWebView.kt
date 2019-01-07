package com.hyeran.android.tooc.reserve

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hyeran.android.tooc.R
import kotlinx.android.synthetic.main.activity_kakaopay_web_view.*

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

    }

}
