package com.tooc.android.tooc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.tooc.android.tooc.map.MapFragment
import com.tooc.android.tooc.map.MapMorePreviewFragment
import com.tooc.android.tooc.mypage.MypageFragment
import com.tooc.android.tooc.reserve.NoReserveFragment
import com.tooc.android.tooc.reserve.ReserveFragment
import com.tooc.android.tooc.reserve_state.ReserveStateFragment
import com.tooc.android.tooc.ship.ShipFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.ctx


class MainActivity : AppCompatActivity() {

    var args: Bundle = Bundle()

    var storeIdx: Int = 0

    var backKeyPressedTime: Long = 0

//    override fun onBackPressed() {
//        Log.d("TAGG",System.currentTimeMillis().toString())
//        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
//            backKeyPressedTime = System.currentTimeMillis()
//            showGuide()
//            return
//        }
//        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
//            finish()
//        }
////        super.onBackPressed()
//    }

    fun showGuide() {
        var toast: Toast? = null
        toast = Toast.makeText(ctx, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG)
        toast!!.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO 타임리밋값넣어야함!!!!!!!!!!!!!!!!!!
        args!!.putInt("timeLimit", 9)

        init()
    }

    fun init() {
        addFragment(MapFragment())
        iv_search_bottom_tab.isSelected = true
        setOnClickListener()
    }

    fun getStoreIdx(storeIdx: Int) {
        Log.d("@storeIdx통신@", "MainActivity의 getSoreIdx 함수에 들어왔다. storeIdx의 값은? " + storeIdx)
        var mapMorePreviewFragment = MapMorePreviewFragment()
        var bundle = Bundle()
        bundle.putInt("storeIdx", storeIdx)
        mapMorePreviewFragment.arguments = bundle

        replaceFragment(mapMorePreviewFragment)
    }

    fun selectedTabChangeColor(flag: Int) {
        clearSelected()
        var img: ImageView? = null
        when (flag) {
            0 -> {
                img = iv_search_bottom_tab
            }
            1 -> {
                img = iv_reserve_bottom_tab
            }
            2 -> {
                img = iv_ship_bottom_tab
            }
            3 -> {
                img = iv_mypage_bottom_tab
            }
        }
        img?.let {
            img.isSelected = true
        }
    }

    fun setOnClickListener() {
        tab_one_main.setOnClickListener {
            replaceFragment(MapFragment.getInstance())
            selectedTabChangeColor(0)
//            iv_search_bottom_tab.isSelected = true
        }
        tab_two_main.setOnClickListener {
            // 예약/보관 진행 중
            if (SharedPreferencesController.instance!!.getPrefBooleanData("is_reserve")) {
                replaceFragment(ReserveStateFragment.getInstance())
            }
            // 예약/보관 없을 시
            else {
                replaceFragment(NoReserveFragment.getInstance())
            }
//            TODO: 123 자리에 서버에서 받은 password값을 넣어야함!!!!!!!!!!!!!!!!
//            replaceFragment(ReserveStateFragment.getInstance("123"))
            selectedTabChangeColor(1)
        }
        tab_three_main.setOnClickListener {
            replaceFragment(ShipFragment.getInstance())
            selectedTabChangeColor(2)
//            var fragment: Fragment = ReserveFragment()
//            fragment.arguments = args
//            replaceFragment(fragment)

        }
        tab_four_main.setOnClickListener {
            replaceFragment(MypageFragment.getInstance())
            selectedTabChangeColor(3)
        }
    }


    fun getTimeSettingDialog(tsmmddee: String, tshh: Int, tsmm: Int, ttmmddee: String, tthh: Int, ttmm: Int, tsValue: Int, ttValue: Int, storeIdx: Int) {

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
//        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun replaceFragment2(fragment: Fragment) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.frame_main, fragment)
            transaction.commit()
    }

}