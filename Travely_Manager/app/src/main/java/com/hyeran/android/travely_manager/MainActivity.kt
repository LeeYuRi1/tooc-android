package com.hyeran.android.travely_manager

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator

import com.hyeran.android.travely_manager.mypage.MypageFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.Manifest.permission
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.util.Log
import android.widget.ImageView
import com.hyeran.android.travely_manager.db.SharedPreferencesController
import com.hyeran.android.travely_manager.model.ReserveDetailResponseData
import com.hyeran.android.travely_manager.network.NetworkService

import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {
    lateinit var reserveCode : String
    lateinit var networkService : NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(ReserveConfirmFragment.getInstance())

        checkDangerousPermission()

        iv_qr_bottom_tab.isSelected = true

        setOnClickListener()
    }


    //QR스캐너
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result.contents == null) {
                // 취소됨

                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                // 스캔된 QRCode --> result.getContents()
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                //TODO qr코드 스캔값 넣어야함!!
                reserveCode = result.contents
                Log.d("TAGGGG","QWEQWEQWEQWEQWEQWEQWEQWEQWEQWEQWEQWEQQWEQWEQWQWEQWEQWEQWEQWE")
                //     replaceFragment(ReserveDetailFragment())
                qrCode("123")
                // (ctx as MainActivity).qrCode("123")

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    //qrCode
    fun qrCode(reserveNumberConfirm :String){
        var args = Bundle()
        var fragment = ReserveDetailFragment()
        args.putString("reserveCode", reserveNumberConfirm)
        fragment.arguments = args
        replaceFragment(fragment)
//        if(reserveNumberConfirm == "123") {
//            Log.d("TAGGGG","RTYRTYTYT")
//            replaceFragment(ReserveDetailFragment())
//        }
//        if(reserveNumberConfirm == "123") {
//            Log.d("TAGGGG","RTYRTYTYT")
//            replaceFragment(ReserveDetailFragment())
//        }
    }

    //photo
    fun gotophotoConfirm(){
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 999)
    }

    fun setOnClickListener() {
        tab_one_main.setOnClickListener {
            replaceFragment(ReserveConfirmFragment())
            selectedTabChangeColor(0)
        }
        tab_two_main.setOnClickListener {
            replaceFragment(ReserveStorageListFragment())
            selectedTabChangeColor(1)
        }
        tab_three_main.setOnClickListener {
            //            replaceFragment(ShipFragment())
            replaceFragment(ShipFragment.getInstance())
            selectedTabChangeColor(2)
        }
        tab_four_main.setOnClickListener {
            replaceFragment(MypageFragment())
            selectedTabChangeColor(3)
        }
    }

    fun selectedTabChangeColor(flag : Int) {
        clearSelected()
        var img : ImageView? = null
        when(flag) {
            0 -> {
                img = iv_qr_bottom_tab
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

    private fun clearSelected() {
        iv_qr_bottom_tab.isSelected = false
        iv_reserve_bottom_tab.isSelected = false
        iv_ship_bottom_tab.isSelected = false
        iv_mypage_bottom_tab.isSelected = false
    }

    fun addFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame_main, fragment)
        transaction.commit()
    }

    fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_main, fragment)
//        transaction.commit()
        transaction.commitAllowingStateLoss()
    }

    private fun checkDangerousPermission() {
        val permissions = arrayOf<String>(permission.CAMERA)

        var permissionCheck = PackageManager.PERMISSION_GRANTED
        for (i in permissions.indices) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i])
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break
            }
        }
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show()

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함", Toast.LENGTH_LONG).show()
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1)
            }
        }
    }



}