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
import android.util.Log
import android.widget.ImageView
import com.hyeran.android.travely_manager.db.SharedPreferencesController
import com.hyeran.android.travely_manager.model.ReserveDetailResponseData
import com.hyeran.android.travely_manager.mypage.CashInfoDialog
import com.hyeran.android.travely_manager.network.ApplicationController
import com.hyeran.android.travely_manager.network.NetworkService
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReserveDetailFragment : Fragment() {


    lateinit var networkService : NetworkService

    lateinit var v : View

    var reserveCode : String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_reserve_detail, container, false)

        networkService = ApplicationController.instance.networkService

        (ctx as MainActivity).selectedTabChangeColor(1)

//        v.btn_store_reserve_detail.setOnClickListener {
//
//            if(!cb_pay_agree.isChecked) {
//                CashInfoDialog(context).show()
//            }
//            else{
//                StorePhotoDialog(context).show()
//            }
//        }

        var bundle : Bundle? = arguments
        reserveCode = bundle!!.getString("reserveCode")

        toast(reserveCode)

        getStoreIdxReserveCodeResponse()

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

    private fun getStoreIdxReserveCodeResponse() {

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        var storeIdx: Int = SharedPreferencesController.instance!!.getPrefIntegerData("storeIdx")

        val getStoreIdxReserveCodeResponse = networkService.getStoreIdxReserveCodeResponse(jwt, reserveCode, storeIdx)

        getStoreIdxReserveCodeResponse!!.enqueue(object : Callback<ReserveDetailResponseData> {
            override fun onFailure(call: Call<ReserveDetailResponseData>, t: Throwable) {
            }

            override fun onResponse(call: Call<ReserveDetailResponseData>, response: Response<ReserveDetailResponseData>) {
                Log.d("@@@@@@@@@@@", "reserveCode: "+reserveCode+", storeIdx: "+storeIdx)

                response?.let {
                    when (it.code()) {
                        200 -> {
                            toast("예약코드 조회 성공")

                        }
                        400 -> {
                            toast("예약 및 보관내역 없음")
                        }
                        403 -> {
                            toast("인증 에러")
                        }
                        500 -> {
                            toast("서버 에러")
                        }
                        else -> {
                            toast("error")
                        }
                    }
                }
            }

        })
    }
}