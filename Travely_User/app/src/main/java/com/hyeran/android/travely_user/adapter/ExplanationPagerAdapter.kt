package com.hyeran.android.travely_user.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.hyeran.android.travely_user.join.Explanation1Fragment
import com.hyeran.android.travely_user.join.Explanation2Fragment
import com.hyeran.android.travely_user.join.Explanation3Fragment
import com.hyeran.android.travely_user.join.LanguageFragment

class ExplanationPagerAdapter(fm : FragmentManager, val fragmentCount : Int) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when(position){
            0 -> return LanguageFragment()
            1 -> return Explanation1Fragment()
            2 -> return Explanation2Fragment()
            3 -> return Explanation3Fragment()
            else -> return null
        }
    }
    override fun getCount(): Int = fragmentCount

}