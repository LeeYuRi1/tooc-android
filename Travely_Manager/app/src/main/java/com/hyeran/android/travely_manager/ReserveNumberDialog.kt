package com.hyeran.android.travely_manager

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.EditText
import kotlinx.android.synthetic.main.dialog_reserve_number.*

class ReserveNumberDialog(val ctx : Context?) : Dialog(ctx){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        requestWindowFeature(Window.FEATURE_LEFT_ICON)
        window.setBackgroundDrawable(ColorDrawable(Color.GRAY))
        setContentView(R.layout.dialog_reserve_number)

        var etReserveNumber = findViewById(R.id.et_reserve_number) as EditText

        btn_reserve_number_confirm.setOnClickListener{
            //만약 password가 일치하면 인텐트 틀리면 토스트
            dismiss()
            (ctx as MainActivity).qrCode(etReserveNumber.text.toString())

        }

    }
}