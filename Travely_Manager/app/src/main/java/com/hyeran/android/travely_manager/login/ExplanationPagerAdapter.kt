package com.hyeran.android.travely_manager.login

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup

class ExplanationPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var items: ArrayList<Fragment> = ArrayList()

    fun addItem(fragment: Fragment) {
        items.add(fragment)
    }

    override fun getItem(position: Int): Fragment? {
        return items[position]
    }

    override fun getCount(): Int = items.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

}