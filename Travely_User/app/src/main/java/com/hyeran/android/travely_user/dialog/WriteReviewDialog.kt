package com.hyeran.android.travely_user.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.hyeran.android.travely_user.R
import kotlinx.android.synthetic.main.dialog_writereview.*

class WriteReviewDialog(ctx : Context?) : Dialog(ctx){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_writereview)

        btn_cancle_writereview.setOnClickListener {
            dismiss()
        }

    }

}