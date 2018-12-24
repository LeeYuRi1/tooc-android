package com.hyeran.android.travely_user

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.dialog_reserve_cancel_password.*
import kotlinx.android.synthetic.main.dialog_reserve_cancel_password_confirm.*
import kotlinx.android.synthetic.main.dialog_reserve_complete.*

class ReserveCancelPasswordConfirmDialog(ctx:Context?) : Dialog(ctx){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.YELLOW))
        setContentView(R.layout.dialog_reserve_cancel_password_confirm)

        btn_reserve_cancel_complete.setOnClickListener {
            dismiss()

            //지도 초기화면으로 보내야함!!
        }
    }
}