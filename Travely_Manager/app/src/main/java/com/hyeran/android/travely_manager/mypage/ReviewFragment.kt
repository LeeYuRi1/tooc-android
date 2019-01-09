package com.hyeran.android.travely_manager.mypage


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hyeran.android.travely_manager.R
import com.hyeran.android.travely_manager.db.SharedPreferencesController
import com.hyeran.android.travely_manager.model.ReviewResponseData
import com.hyeran.android.travely_manager.model.ReviewUserImgData
import com.hyeran.android.travely_manager.model.StoreGradeReviewData
import com.hyeran.android.travely_manager.network.ApplicationController
import com.hyeran.android.travely_manager.network.NetworkService
import kotlinx.android.synthetic.main.fragment_mypage.*
import kotlinx.android.synthetic.main.fragment_review.*
import kotlinx.android.synthetic.main.fragment_review.view.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewFragment : Fragment() {

    lateinit var networkService: NetworkService

    lateinit var reviewListRVAdapter: ReviewListRVAdapter

    val dataList: ArrayList<ReviewUserImgData> by lazy {
        ArrayList<ReviewUserImgData>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_review, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        networkService = ApplicationController.instance.networkService

        setRecyclerView()
        getReviewResponse()
    }

    private fun setRecyclerView() {
        reviewListRVAdapter = ReviewListRVAdapter(context, dataList)
        rv_review_list.adapter = reviewListRVAdapter
        rv_review_list.layoutManager = LinearLayoutManager(context)
    }

    private fun getReviewResponse() {
        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")

        val getReviewResponse = networkService.getReviewResponse(jwt)

        getReviewResponse!!.enqueue(object : Callback<ReviewResponseData> {
            override fun onFailure(call: Call<ReviewResponseData>, t: Throwable) {
                toast("즐겨찾기 통신 실패")
            }

            override fun onResponse(call: Call<ReviewResponseData>, response: Response<ReviewResponseData>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            tv_num_review.text = response.body()!!.storeGradeReview.count.toString()
                            tv_num2_review.text = response.body()!!.storeGradeReview.count.toString()
                            tv_grade_review.text = response.body()!!.storeGradeReview.grade.toString()
                            ratingBar_review.rating = response.body()!!.storeGradeReview.grade.toFloat()

                            var dataList_review: ArrayList<ReviewUserImgData> = response.body()!!.reviewUserImgResponseDtos
                            if (dataList_review.size > 0) {
                                var position = reviewListRVAdapter.itemCount
                                reviewListRVAdapter.dataList.addAll(dataList_review)
                                reviewListRVAdapter.notifyItemInserted(position)
                            } else {

                            }

                            toast("즐겨찾기 조회 성공")
                        }
                        500 -> {
                            toast("서버 에러")
                        }
                        else -> {
                            toast("error")
                            toast(it.message())

                        }
                    }
                }
            }

        })
    }
}
