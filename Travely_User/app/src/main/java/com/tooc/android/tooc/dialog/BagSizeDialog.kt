package com.tooc.android.tooc.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.tooc.android.tooc.R
import kotlinx.android.synthetic.main.dialog_bag_size.*

class BagSizeDialog(val ctx : Context?) : Dialog(ctx) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_bag_size)

        btn_x_bag_size.setOnClickListener {
            dismiss()
        }

    }
}
