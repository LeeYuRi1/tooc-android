package com.hyeran.android.travely_manager

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.dialog_store_photo.*

class StorePhotoDialog(ctx : Context?) : Dialog(ctx) {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_store_photo)
        btn_ok_dialog_store_photo.setOnClickListener {
            dismiss()
        }
    }
}