package com.hyeran.android.tooc.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.adapter.MypageRecentStoreAdapter
import com.hyeran.android.tooc.model.StoreInfoResponseData
import com.hyeran.android.tooc.model.mypage.FavoriteResponseData
import com.hyeran.android.tooc.model.mypage.InquiryResponseData
import com.hyeran.android.tooc.model.mypage.ReviewSaveResponseData
import com.hyeran.android.tooc.network.ApplicationController
import com.hyeran.android.tooc.network.NetworkService
import kotlinx.android.synthetic.main.dialog_writereview.*
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteReviewDialog(ctx : Context?) : Dialog(ctx){

    lateinit var networkService: NetworkService

    lateinit var mypageRecentStoreAdapter : MypageRecentStoreAdapter

    val dataList: ArrayList<StoreInfoResponseData> by lazy {
        ArrayList<StoreInfoResponseData>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_writereview)

        networkService = ApplicationController.instance.networkService

        mypageRecentStoreAdapter = MypageRecentStoreAdapter(context, dataList)

        btn_cancle_writereview.setOnClickListener {
            dismiss()
        }

        btn_ok_writereview.setOnClickListener {
            if(et_review_writereview.text.length == 0) {
                Toast.makeText(context, "리뷰를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                postWriteReviewResponse()
            }
        }

    }



    private fun postWriteReviewResponse(){
        var storeIdx = mypageRecentStoreAdapter.reviewStoreIdx
        var content = et_review_writereview.text.toString()
        var rating = ratingBar_writereview.rating.toLong()

        var ReviewSave: ReviewSaveResponseData

        ReviewSave = ReviewSaveResponseData(storeIdx, content, rating)   //storeidx꺼내서 쓰기

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        val postReviewSaveResponse = networkService.postReviewSaveResponse("application/json", jwt, ReviewSave)

        postReviewSaveResponse!!.enqueue(object : Callback<ReviewSaveResponseData> {
            override fun onFailure(call: Call<ReviewSaveResponseData>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<ReviewSaveResponseData>?, response: Response<ReviewSaveResponseData>?) {
                response?.let {
                    when (it.code()) {
                        201 -> {
                            Toast.makeText(context, "리뷰 저장 성공", Toast.LENGTH_SHORT).show()
                        }
                        400 -> {
                            Toast.makeText(context, "잘못 된 정보 주입 시도", Toast.LENGTH_SHORT).show()
                        }
                        500 -> {
                            Toast.makeText(context, "서버 에러", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })


    }



}