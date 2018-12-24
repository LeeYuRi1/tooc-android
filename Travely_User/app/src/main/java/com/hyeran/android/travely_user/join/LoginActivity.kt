package com.hyeran.android.travely_user.join

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hyeran.android.travely_user.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setClickListener()
    }

    private fun setClickListener() {
        tv_join_login.setOnClickListener {
            val intent = Intent(applicationContext, JoinActivity::class.java)
            startActivity(intent)
        }
    }
}
