package com.hyeran.android.tooc.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.model.mypage.ReviewSaveResponseData
import com.hyeran.android.tooc.network.ApplicationController
import com.hyeran.android.tooc.network.NetworkService
import kotlinx.android.synthetic.main.dialog_writereview.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteReviewDialog(ctx : Context?) : Dialog(ctx){

    lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_writereview)

        networkService = ApplicationController.instance.networkService

        btn_cancle_writereview.setOnClickListener {
            dismiss()
        }
    }


//    private fun postWriteReviewResponse(){
//        var content = et_review_writereview.text.toString()
//        var rating = ratingBar_writereview.rating
//
//        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
//        val postReviewSaveResponse = networkService.postReviewSaveResponse("application/json", jwt,)
//
//        postReviewSaveResponse!!.enqueue(object : Callback<ReviewSaveResponseData> {
//            override fun onFailure(call: Call<ReviewSaveResponseData>?, t: Throwable?) {
//            }
//
//            override fun onResponse(call: Call<ReviewSaveResponseData>?, response: Response<ReviewSaveResponseData>?) {
//            }
//
//        })
//
//
//    }
//
//

}