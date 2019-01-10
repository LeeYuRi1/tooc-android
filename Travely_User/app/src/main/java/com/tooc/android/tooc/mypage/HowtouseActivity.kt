package com.tooc.android.tooc.mypage

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.tooc.android.tooc.R
import com.tooc.android.tooc.adapter.ExplanationPagerAdapter
import com.tooc.android.tooc.join.*
import com.tooc.android.tooc.network.NetworkService
import kotlinx.android.synthetic.main.activity_howtouse.*


class HowtouseActivity : AppCompatActivity() {

    lateinit var networkService : NetworkService

    lateinit var explanationPagerAdapter : ExplanationPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_howtouse)

        init()

        back_howtouse.setOnClickListener {
            //(ctx as MainActivity).replaceFragment(SetFragment())
            finish()
        }
    }

    private fun init() {
        SharedPreferencesController.instance!!.setPrefData("Explanation", true)
        startExplanation()
    }

    private fun startExplanation(){
        explanationPagerAdapter = ExplanationPagerAdapter(supportFragmentManager)
        explanationPagerAdapter.addItem(Explanation1Fragment.newInstance("1"))
        explanationPagerAdapter.addItem(Explanation2Fragment.newInstance("2"))
        explanationPagerAdapter.addItem(Explanation3Fragment.newInstance("3"))
        explanationPagerAdapter.addItem(Explanation4Fragment.newInstance("4"))
        explanationPagerAdapter.addItem(WelcomeFragment.newInstance("5"))


        vp_explanation1.offscreenPageLimit = 5
        indicator_explanation1.setItemMargin(10)
        indicator_explanation1.setAnimDuration(300)
        indicator_explanation1.createDotPanel(explanationPagerAdapter.count, R.drawable.indicator_empty_circle, R.drawable.indicator_full_circle)

        vp_explanation1.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                indicator_explanation1.selectDot(position)
            }
        })
        vp_explanation1.currentItem = 0
        vp_explanation1.adapter = explanationPagerAdapter
    }
}
