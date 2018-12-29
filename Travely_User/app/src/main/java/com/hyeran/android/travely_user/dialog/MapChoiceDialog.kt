package com.hyeran.android.travely_user.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import com.hyeran.android.travely_user.R
import kotlinx.android.synthetic.main.dialog_map_choice.*

class MapChoiceDialog(ctx: Context?) : Dialog(ctx) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_map_choice)

        btn_dialog_map_choice_google.setOnClickListener {
            val url : String = "http://maps.google.com/maps?saddr=37.537229,127.005515&daddr=37.4979502,127.0276368"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.setPackage("com.google.android.apps.maps")

            dismiss()
        }

        btn_dialog_map_choice_kakao.setOnClickListener {
            val url : String = "daummaps://route?sp=37.537229,127.005515&ep=37.4979502,127.0276368&by=PUBLICTRANSIT"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            dismiss()
        }

        btn_dialog_map_choice_cancel.setOnClickListener {
            dismiss()
        }

    }
}
