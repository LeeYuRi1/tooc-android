package com.hyeran.android.travely_user.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.map.MapFragment
import com.hyeran.android.travely_user.reserve_state.ReserveStateFragment
import kotlinx.android.synthetic.main.dialog_reserve_cancel_password_confirm.*

class ReserveCancelPasswordConfirmDialog(val ctx:Context?) : Dialog(ctx){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_reserve_cancel_password_confirm)

        btn_reserve_cancel_complete.setOnClickListener {
            dismiss()
            //(ctx as MainActivity).replaceFragment(ReserveStateFragment())
            //지도 초기화면으로 보내야함!!
        }
    }
}