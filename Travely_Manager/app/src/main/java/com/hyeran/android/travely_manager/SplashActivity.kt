package com.hyeran.android.travely_manager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.hyeran.android.travely_manager.login.LoginActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed( {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
