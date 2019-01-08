package com.hyeran.android.travely_manager


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reserve_detail.*
import kotlinx.android.synthetic.main.fragment_reserve_detail.view.*
import android.R.attr.data
import android.support.v4.app.NotificationCompat.getExtras
import android.graphics.Bitmap
import android.widget.ImageView
import com.hyeran.android.travely_manager.mypage.CashInfoDialog
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast


class ReserveDetailFragment : Fragment() {

    lateinit var v : View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_reserve_detail, container, false)

        (ctx as MainActivity).selectedTabChangeColor(1)

        v.btn_store_reserve_detail.setOnClickListener {

            if(!cb_pay_agree.isChecked) {
                CashInfoDialog(context).show()
            }
            else{
                StorePhotoDialog(context).show()
            }
        }

        var bundle : Bundle? = arguments
        var reserveCode : String = bundle!!.getString("reserveCode")

        toast(reserveCode)

        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == 999) {
//            if (resultCode == Activity.RESULT_OK) { // 이 두 부분은 형식적
//                data?.let {
//                    val image = data.extras.get("data") as Bitmap
//                    val imageview = v.findViewById(R.id.iv_test_reserve_detail) as ImageView //sets imageview as the bitmap
//                    imageview.setImageBitmap(image)
//                }
//            }
//        }
    }
}