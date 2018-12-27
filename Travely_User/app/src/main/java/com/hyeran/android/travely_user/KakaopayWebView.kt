package com.hyeran.android.travely_user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
    }
}
