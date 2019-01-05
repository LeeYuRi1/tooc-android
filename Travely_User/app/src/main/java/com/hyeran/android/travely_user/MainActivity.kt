package com.hyeran.android.travely_user

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.hyeran.android.travely_user.map.MapFragment
import com.hyeran.android.travely_user.map.MapMorePreviewFragment
import com.hyeran.android.travely_user.mypage.MypageFragment
import com.hyeran.android.travely_user.reserve.ReserveFragment
import com.hyeran.android.travely_user.reserve_state.ReserveStateFragment
import com.hyeran.android.travely_user.ship.ShipFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_reserve.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    var args: Bundle = Bundle()

    var storeIdx: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //TODO 타임리밋값넣어야함!!!!!!!!!!!!!!!!!!
        args!!.putInt("timeLimit",9)

        init()
    }

    fun init() {
        addFragment(MapFragment())
        iv_search_bottom_tab.isSelected = true
        setOnClickListener()
    }

    fun getStoreIdx(storeIdx : Int) {
        Log.d("@storeIdx통신@", "MainActivity의 getSoreIdx 함수에 들어왔다. storeIdx의 값은? "+ storeIdx)
        var mapMorePreviewFragment = MapMorePreviewFragment()
        var bundle = Bundle()
        bundle.putInt("storeIdx", storeIdx)
        mapMorePreviewFragment.arguments = bundle

        replaceFragment(mapMorePreviewFragment)
    }

    fun setOnClickListener() {
        tab_one_main.setOnClickListener {
            replaceFragment(MapFragment())
            clearSelected()
            iv_search_bottom_tab.isSelected = true
        }
        tab_two_main.setOnClickListener {
            //TODO: 123 자리에 서버에서 받은 password값을 넣어야함!!!!!!!!!!!!!!!!
            replaceFragment(ReserveStateFragment.getInstance("123"))
            clearSelected()
            iv_reserve_bottom_tab.isSelected = true
        }
        tab_three_main.setOnClickListener {

            clearSelected()
            iv_ship_bottom_tab.isSelected = true

            var fragment: Fragment = ReserveFragment()
            fragment.arguments = args
            replaceFragment(fragment)

        }
        tab_four_main.setOnClickListener {
            clearSelected()
            iv_mypage_bottom_tab.isSelected = true
            replaceFragment(MypageFragment())
        }
    }

    fun getTimeSettingDialog(tsmmddee: String, tshh: Int, tsmm: Int, ttmmddee: String, tthh: Int, ttmm: Int, tsValue: Int, ttValue: Int,storeIdx:Int) {

        args!!.putString("smmddee", tsmmddee)
        args!!.putInt("shh", tshh)
        args!!.putInt("smm", tsmm)
        args!!.putString("tmmddee", ttmmddee)
        args!!.putInt("thh", tthh)
        args!!.putInt("tmm", ttmm)
        args!!.putInt("svalue", tsValue)
        args!!.putInt("tvalue", ttValue)
        args!!.putInt("storeIdx", storeIdx)

        var fragment: Fragment = ReserveFragment()
        fragment.arguments = args
        replaceFragment(fragment)
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

}