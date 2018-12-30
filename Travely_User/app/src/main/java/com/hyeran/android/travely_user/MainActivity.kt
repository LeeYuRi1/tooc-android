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
import kotlinx.android.synthetic.main.fragment_reserve.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    var args : Bundle = Bundle()

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
            //123 자리에 서버에서 받은 password값을 넣어야함
            replaceFragment(ReserveStateFragment.getInstance("123"))

        }
        tab_three_main.setOnClickListener {
            //123 자리에 서버에서 받은 password값을 넣어야함!!!!!!!!!!!!!!!!!
//            replaceFragment(ReserveFragment())

            args!!.putString("smmddee", "")
            args!!.putString("shh", "")
            args!!.putString("smm", "")
            args!!.putString("tmmddee", "")
            args!!.putString("thh", "")
            args!!.putString("tmm", "")

            var fragment : Fragment = ReserveFragment()
            fragment.arguments = args
            replaceFragment(fragment)

        }
        tab_four_main.setOnClickListener {
            clearSelected()
            iv_mypage_bottom_tab.isSelected = true
            replaceFragment(MypageFragment())
        }
    }

    var smmddee : String? = null
    var tmmddee : String? = null
    var shh : String? = null
    var smm : String? = null
    var thh : String? = null
    var tmm : String? = null


    fun getTimeSettingDialog(tsmmddee: String,tshh:String,tsmm:String,ttmmddee:String,tthh:String,ttmm:String) {
        smmddee = tsmmddee
        shh = tshh
        smm = tsmm
        tmmddee = ttmmddee
        thh = tthh
        tmm = ttmm
        toast("날짜" + smmddee.toString() + "시간" + shh.toString() + "분" + smm.toString())

        args!!.putString("smmddee", smmddee)
        args!!.putString("shh", shh)
        args!!.putString("smm", smm)
        args!!.putString("tmmddee", tmmddee)
        args!!.putString("thh", thh)
        args!!.putString("tmm", tmm)

        var fragment: Fragment = ReserveFragment()
        fragment.arguments = args
        replaceFragment(fragment)

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
