package com.hyeran.android.tooc.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hyeran.android.tooc.MainActivity
import com.hyeran.android.tooc.adapter.MypageMyReviewAdapter
import com.hyeran.android.tooc.network.ApplicationController
import com.hyeran.android.tooc.network.NetworkService
import com.hyeran.android.tooc.model.mypage.ReviewLookupData
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.adapter.MypageLikeAdapter
import com.hyeran.android.tooc.data.MypageMyReviewData
import com.hyeran.android.tooc.model.StoreInfoResponseData
import kotlinx.android.synthetic.main.fragment_like.*
import kotlinx.android.synthetic.main.fragment_myreview.*
import kotlinx.android.synthetic.main.item_myreview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyreviewFragment:Fragment() {
    lateinit var networkService : NetworkService

    lateinit var mypageMyReviewAdapter: MypageMyReviewAdapter

    lateinit var v : View

    val dataList : ArrayList<ReviewLookupData> by lazy {
        ArrayList<ReviewLookupData>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_myreview, container, false)
        init()
        getReviewLookupResponse()
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()

        iv_back_mypage.setOnClickListener {
            (ctx as MainActivity).replaceFragment(MypageFragment())
        }
    }

    private fun init() {
        networkService = ApplicationController.instance.networkService
    }

    private fun getReviewLookupResponse() {
        var jwt : String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        var getReviewLookupResponse = networkService.getReviewLookupResponse(jwt)
        getReviewLookupResponse!!.enqueue(object : Callback<ArrayList<ReviewLookupData>>{
            override fun onFailure(call: Call<ArrayList<ReviewLookupData>>, t: Throwable) {
            }
            override fun onResponse(call: Call<ArrayList<ReviewLookupData>>, response: Response<ArrayList<ReviewLookupData>>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            var dataList_myreview : ArrayList<ReviewLookupData> = response.body()!!

                            if (dataList_myreview.size > 0) {
                                val position = mypageMyReviewAdapter.itemCount
                                mypageMyReviewAdapter.dataList.addAll(dataList_myreview)
                                mypageMyReviewAdapter.notifyItemInserted(position)
                            } else {

                            }

//                            Glide.with(this@MyreviewFragment)
//                                    .load(response.body()!![1].storeImgUrl)
//                                    .into(iv_storeimage_myreview)

                          //  tv_name_myreview.text = response.body()!![1].storeName

                           // tv_area_myreview.text = response.body()!![1].address

                           // tv_content_myreview.text = response.body()!![1].content


                            toast("리뷰 조회 성공")
                        }
                        204 -> {
                            toast("작성 리뷰 없음")
                        }
                        400 -> {
                            toast("잘못된 접근")
                        }
                        500 -> {
                            toast("서버에러")
                        }
                        else -> {
                            toast("error")
                        }
                    }
                }
            }
        })
    }

    private fun setRecyclerView() {
//        var dataList: ArrayList<MypageMyReviewData> = ArrayList()
//        dataList.add(MypageMyReviewData("동대문엽기떡볶이 홍대점", "서울시 마포구 샬라샬라", "2018","3","11", "위치가 역이랑 가까워서 좋았어요!\n건물도 깨끗했고, 상가 근처에 예쁜 카페가 많아서 구경할거리도 많더라구요!"))
//        dataList.add(MypageMyReviewData("동대문엽기떡볶이 강남점", "서울시 마포구 샬라샬라2", "2017","1","23","감사합니다." ))
//        dataList.add(MypageMyReviewData("동대문엽기떡볶이 수유점", "서울시 마포구 샬라샬라3", "2016","7","9", "ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ"))

        mypageMyReviewAdapter = MypageMyReviewAdapter(activity!!, dataList)
        rv_review_myreview.adapter = mypageMyReviewAdapter
        rv_review_myreview.layoutManager = LinearLayoutManager(activity)
    }

}