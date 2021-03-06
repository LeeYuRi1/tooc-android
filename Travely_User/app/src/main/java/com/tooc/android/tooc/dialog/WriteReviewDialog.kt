package com.tooc.android.tooc.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.tooc.android.tooc.MainActivity
import com.tooc.android.tooc.R
import com.tooc.android.tooc.adapter.MypageRecentStoreAdapter
import com.tooc.android.tooc.model.store.StoreInfoResponseData
import com.tooc.android.tooc.model.mypage.ReviewSaveResponseData
import com.tooc.android.tooc.mypage.MypageFragment
import com.tooc.android.tooc.network.ApplicationController
import com.tooc.android.tooc.network.NetworkService
import kotlinx.android.synthetic.main.dialog_writereview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteReviewDialog(var ctx : Context?,var storeIdx:Int) : Dialog(ctx){

    lateinit var networkService: NetworkService

    lateinit var mypageRecentStoreAdapter : MypageRecentStoreAdapter

    var onDismissListener : android.support.v7.widget.PopupMenu.OnDismissListener? = null

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
        //var storeIdx = mypageRecentStoreAdapter.reviewStoreIdx
        var content = et_review_writereview.text.toString()
        var rating = ratingBar_writereview.rating.toLong()

        var reviewSave: ReviewSaveResponseData

        reviewSave = ReviewSaveResponseData(storeIdx, content, rating)   //storeidx꺼내서 쓰기
//        Log.d("TAGGGG","storeIdx = "+storeIdx+"   content")

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        val postReviewSaveResponse = networkService.postReviewSaveResponse("application/json", jwt, reviewSave)

        postReviewSaveResponse.enqueue(object : Callback<ReviewSaveResponseData> {
            override fun onFailure(call: Call<ReviewSaveResponseData>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<ReviewSaveResponseData>?, response: Response<ReviewSaveResponseData>?) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            (ctx as MainActivity).replaceFragment(MypageFragment())
                            dismiss()

                        }
                        201 -> {
                            dismiss()
                        }
                        400 -> {
                        }
                        500 -> {
                        }
                        else -> {
                        }
                    }
                }
            }

        })


    }



}