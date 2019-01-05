package com.hyeran.android.travely_user.dialog

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.R.id.btn_dialog_map_choice_google
import kotlinx.android.synthetic.main.dialog_map_choice.*
import org.jetbrains.anko.startActivity
import kotlin.math.log

class MapChoiceDialog(ctx: Context?,val lat : Double,val lng : Double) : Dialog(ctx) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_map_choice)

        setOnBtnClickListener()

    }

    private fun setOnBtnClickListener() {
        btn_dialog_map_choice_google.setOnClickListener {

            var package1 : String = "com.google.android.apps.maps"
            var url1 : String = "http://maps.google.com/maps?saddr=" + lat.toString() + "," + lng.toString() + "&daddr=37.4979502,127.0276368"
            var intent1 = Intent(Intent.ACTION_VIEW, Uri.parse(url1))
            intent1.setPackage(package1)

            try {
                context.startActivity(intent1)
            } catch (e : android.content.ActivityNotFoundException) {
                var url3 : String = "http://play.google.com/store/apps/details?id=" + package1
                var intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(url3))
                context.startActivity(intent3)
            }

            dismiss()
        }

        btn_dialog_map_choice_kakao.setOnClickListener {

            var package2 : String = "net.daum.android.map"
            var url2 : String = "daummaps://route?sp=" + lat.toString() + "," + lng.toString() + "&ep=37.4979502,127.0276368&by=CAR"
//            val url2 : String = "daummaps://route?sp=37.537229,127.005515&ep=37.4979502,127.0276368&by=CAR"
            var intent2 = Intent(Intent.ACTION_VIEW, Uri.parse(url2))
            intent2.setPackage("net.daum.android.map")

            try {
                context.startActivity(intent2)
            } catch (e : android.content.ActivityNotFoundException) {
                var url4 : String = "http://play.google.com/store/apps/details?id=" + package2
                var intent4 = Intent(Intent.ACTION_VIEW, Uri.parse(url4))
                context.startActivity(intent4)
            }

            dismiss()
        }

        btn_dialog_map_choice_cancel.setOnClickListener {
            dismiss()
        }
    }
}
