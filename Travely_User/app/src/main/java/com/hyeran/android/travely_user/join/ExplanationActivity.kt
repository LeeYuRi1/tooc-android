package com.hyeran.android.travely_user.join

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.ExplanationPagerAdapter
import kotlinx.android.synthetic.main.activity_explanation.*

class ExplanationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explanation)
    }

    private fun configureExplantion() {
        vp_explanation.adapter = ExplanationPagerAdapter(supportFragmentManager, 4)
//
//        vp_explanation.addOnAdapterChangeListener(object : ViewPager.OnPageChangeListener) {
//
        }

    }
