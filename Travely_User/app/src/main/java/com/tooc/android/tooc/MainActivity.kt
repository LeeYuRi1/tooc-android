package com.tooc.android.tooc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.widget.ImageView
import com.tooc.android.tooc.map.MapFragment
import com.tooc.android.tooc.map.MapMorePreviewFragment
import com.tooc.android.tooc.mypage.MypageFragment
import com.tooc.android.tooc.reserve.NoReserveFragment
import com.tooc.android.tooc.reserve.ReserveFragment
import com.tooc.android.tooc.reserve_state.ReserveStateFragment
import com.tooc.android.tooc.ship.ShipFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var args: Bundle = Bundle()
    var storeIdx: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        addFragment(MapFragment())
        iv_search_bottom_tab.isSelected = true
        setOnClickListener()
        args.putInt("timeLimit", 9)
    }

    fun setOnClickListener() {
        tab_one_main.setOnClickListener {
            replaceFragment(MapFragment.getInstance())
            selectedTabChangeColor(0)
        }
        tab_two_main.setOnClickListener {
            if (SharedPreferencesController.instance!!.getPrefBooleanData("is_reserve"))
                replaceFragment(ReserveStateFragment.getInstance())    // 예약/보관 있을 시
            else replaceFragment(NoReserveFragment.getInstance())   // 예약/보관 없을 시

            selectedTabChangeColor(1)
        }
        tab_three_main.setOnClickListener {
            replaceFragment(ShipFragment.getInstance())
            selectedTabChangeColor(2)
        }
        tab_four_main.setOnClickListener {
            replaceFragment(MypageFragment.getInstance())
            selectedTabChangeColor(3)
        }
    }

    fun selectedTabChangeColor(flag: Int) {
        clearSelected()
        var img: ImageView? = null
        when (flag) {
            0 -> img = iv_search_bottom_tab
            1 -> img = iv_reserve_bottom_tab
            2 -> img = iv_ship_bottom_tab
            3 -> img = iv_mypage_bottom_tab
        }
        img?.let {
            img.isSelected = true
        }
    }

    private fun clearSelected() {
        iv_search_bottom_tab.isSelected = false
        iv_reserve_bottom_tab.isSelected = false
        iv_ship_bottom_tab.isSelected = false
        iv_mypage_bottom_tab.isSelected = false
    }

    private fun addFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame_main, fragment)
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_main, fragment)
        transaction.commit()
    }

    fun replaceFragmentBackStack(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.frame_main, fragment)
        transaction.commit()
    }

    // MapFragment에서 호출
    fun getStoreIdx(storeIdx: Int) {
        var mapMorePreviewFragment = MapMorePreviewFragment()
        var bundle = Bundle()
        bundle.putInt("storeIdx", storeIdx)
        mapMorePreviewFragment.arguments = bundle
        replaceFragment(mapMorePreviewFragment)
    }

    // ReserveTimeSettingDialog에서 호출
    fun getTimeSettingDialog(tsmmddee: String, tshh: Int, tsmm: Int, ttmmddee: String, tthh: Int, ttmm: Int, tsValue: Int, ttValue: Int, storeIdx: Int) {
        args.putString("smmddee", tsmmddee)
        args.putInt("shh", tshh)
        args.putInt("smm", tsmm)
        args.putString("tmmddee", ttmmddee)
        args.putInt("thh", tthh)
        args.putInt("tmm", ttmm)
        args.putInt("svalue", tsValue)
        args.putInt("tvalue", ttValue)
        args.putInt("storeIdx", storeIdx)

        val fragment: Fragment = ReserveFragment()
        fragment.arguments = args
        replaceFragment(fragment)
    }
}
