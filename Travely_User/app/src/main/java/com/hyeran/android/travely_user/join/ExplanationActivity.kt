package com.hyeran.android.travely_user.join

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.ExplanationPagerAdapter
import kotlinx.android.synthetic.main.activity_explanation.*

class ExplanationActivity : AppCompatActivity() {

    lateinit var explanationPagerAdapter : ExplanationPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explanation)

        explanationPagerAdapter = ExplanationPagerAdapter(supportFragmentManager)

        explanationPagerAdapter.addItem(LanguageFragment.newInstance("1"))
        explanationPagerAdapter.addItem(Explanation1Fragment.newInstance("2"))
        explanationPagerAdapter.addItem(Explanation2Fragment.newInstance("3"))
        explanationPagerAdapter.addItem(Explanation3Fragment.newInstance("4"))


        vp_explanation.offscreenPageLimit = 4
        indicator_explanation.setItemMargin(10)
        indicator_explanation.setAnimDuration(300)
        indicator_explanation.createDotPanel(explanationPagerAdapter.count, R.drawable.indicator_empty_circle, R.drawable.indicator_full_circle)

        vp_explanation.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                indicator_explanation.selectDot(position)
               // vp_explanation.reMeasureCurrentPage(vp_explanation.currentItem)


            }
        })
        vp_explanation.setCurrentItem(0)
        vp_explanation.adapter = explanationPagerAdapter
    }
}

