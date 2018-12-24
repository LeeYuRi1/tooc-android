package com.hyeran.android.travely_user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.hyeran.android.travely_user.mypage.MypageFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(OneFragment.getInstance())
        setOnClickListener()
    }

    fun setOnClickListener() {
        tab_one_main.setOnClickListener {
            replaceFragment(OneFragment())
        }
        tab_two_main.setOnClickListener {
            replaceFragment(TwoFragment())
        }
        tab_three_main.setOnClickListener {
            replaceFragment(ThreeFragment())
        }
        tab_four_main.setOnClickListener {
            replaceFragment(MypageFragment())
        }
    }

    fun addFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame_main, fragment)
        transaction.commit()
    }

    fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_main, fragment)
        transaction.commit()
    }
}
