package com.hyeran.android.travely_user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.hyeran.android.travely_user.map.MapFragment
import com.hyeran.android.travely_user.map.MapMorePreviewFragment
import com.hyeran.android.travely_user.mypage.MypageFragment
import com.hyeran.android.travely_user.reserve.ReserveFragment
import com.hyeran.android.travely_user.reserve_state.ReserveStateFragment
import com.hyeran.android.travely_user.ship.ShipFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(MapFragment.getInstance())
        iv_search_bottom_tab.isSelected = true
        setOnClickListener()
    }

    fun setOnClickListener() {
        tab_one_main.setOnClickListener {
            replaceFragment(MapFragment())
            clearSelected()
            iv_search_bottom_tab.isSelected = true
            replaceFragment(MapFragment.getInstance())
        }
        tab_two_main.setOnClickListener {
            clearSelected()
            iv_reserve_bottom_tab.isSelected = true
            replaceFragment(ReserveStateFragment())
//            replaceFragment(MapMorePreviewFragment())
        }
        tab_three_main.setOnClickListener {
            clearSelected()
            iv_ship_bottom_tab.isSelected = true
            replaceFragment(ShipFragment())
//            replaceFragment(ReserveFragment())
        }
        tab_four_main.setOnClickListener {
            clearSelected()
            iv_mypage_bottom_tab.isSelected = true
            replaceFragment(MypageFragment())
        }
    }

    fun temp() {
        replaceFragment(MapMorePreviewFragment())
    }

    fun clearSelected() {
        iv_search_bottom_tab.isSelected = false
        iv_reserve_bottom_tab.isSelected = false
        iv_ship_bottom_tab.isSelected = false
        iv_mypage_bottom_tab.isSelected = false
    }

    fun addFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame_main, fragment)
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_main, fragment)
        transaction.commit()
    }
}
