package com.hyeran.android.travely_manager

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.dialog_storage.*

class StorageDialog(val ctx : Context?) : Dialog(ctx) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_storage)

        btn_reserve_storage_complete.setOnClickListener {
            dismiss()
//            (ctx as MainActivity).replaceFragment(ReserveDetailFragment())
        }

    }
}