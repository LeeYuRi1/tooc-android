package com.hyeran.android.travely_manager

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(ReserveConfirmFragment.getInstance())

        setOnClickListener()
    }


    //QR스캐너
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       // super.onActivityResult(requestCode, resultCode, data)
       // Toast.makeText(this, "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$", Toast.LENGTH_LONG).show()
        //  com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE
        //  = 0x0000c0de; // Only use bottom 16 bits
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result.contents == null) {
                // 취소됨
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                // 스캔된 QRCode --> result.getContents()
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    fun qrCode(reserveNumberConfirm :String){
       if(reserveNumberConfirm == "123") {
           replaceFragment(ReserveDetailFragment())
       }
    }

    fun setOnClickListener() {
        tab_one_main.setOnClickListener {
            replaceFragment(ReserveConfirmFragment())
        }
        tab_two_main.setOnClickListener {
            replaceFragment(ReserveListFragment())
        }
        tab_three_main.setOnClickListener {
            //            replaceFragment(ShipFragment())
            replaceFragment(StorageListFragment())
        }
        tab_four_main.setOnClickListener {
            replaceFragment(FourFragment())
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
