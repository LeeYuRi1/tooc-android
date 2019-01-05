package com.hyeran.android.travely_user.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.dialog_reserve_cancel_password.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReserveCancelPasswordDialog(val ctx : Context?) : Dialog(ctx){

    lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_reserve_cancel_password)

        init()

        var mEtPassword = findViewById(R.id.et_input_password_reserve_cancel) as EditText
        mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)

        var password  = SharedPreferencesController.instance!!.getPrefStringData("user_pw")

        btn_reserve_cancel_password_confirm.setOnClickListener{

            if(password == mEtPassword.text.toString()) {
                dismiss()
                deleteReservationCancelResponse()
            }
            else {
                Toast.makeText(ctx,"비밀번호가 틀렸습니다.",Toast.LENGTH_LONG).show()
                dismiss()
            }
        }
    }

    private fun init() {
        networkService = ApplicationController.instance.networkService
    }

    // 예약 취소 함수
    private fun deleteReservationCancelResponse() {

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        val deleteReservationCancelResponse = networkService.deleteReservationCancelResponse(jwt)

        deleteReservationCancelResponse!!.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("예약 취소", "#####"+t.message)
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            SharedPreferencesController.instance!!.setPrefData("is_reserve", false)
                            ReserveCancelPasswordConfirmDialog(context).show()
                        }
                        500 -> {
                            Toast.makeText(ctx, "서버 에러", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(ctx, "error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

}