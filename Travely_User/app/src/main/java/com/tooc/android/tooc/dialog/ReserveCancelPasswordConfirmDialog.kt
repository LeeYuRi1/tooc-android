package com.tooc.android.tooc.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.tooc.android.tooc.MainActivity
import com.tooc.android.tooc.R
import com.tooc.android.tooc.map.MapFragment
import kotlinx.android.synthetic.main.dialog_reserve_cancel_password_confirm.*


class ReserveCancelPasswordConfirmDialog(val ctx:Context?) : Dialog(ctx){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_reserve_cancel_password_confirm)

        btn_reserve_cancel_complete.setOnClickListener {
            dismiss()
            (ctx as MainActivity).replaceFragment(MapFragment())
            ctx.selectedTabChangeColor(0)
        }
    }
}