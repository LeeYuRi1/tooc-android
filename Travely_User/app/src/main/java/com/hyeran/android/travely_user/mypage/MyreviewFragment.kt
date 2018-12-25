package com.hyeran.android.travely_user.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.MypageLikeAdapter
import com.hyeran.android.travely_user.adapter.MypageMyReviewAdapter
import com.hyeran.android.travely_user.data.MypageMyReviewData
import kotlinx.android.synthetic.main.fragment_like.*
import kotlinx.android.synthetic.main.fragment_myreview.*

class MyreviewFragment:Fragment() {

    lateinit var mypageMyReviewAdapter: MypageMyReviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_myreview, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        var dataList: ArrayList<MypageMyReviewData> = ArrayList()
        dataList.add(MypageMyReviewData("동대문엽기떡볶이 홍대점", "2018-11-3", "감사합니다!~~"))
        dataList.add(MypageMyReviewData("동대문엽기떡볶이 강남점", "2016-11-3", "ㄳㄳ"))
        dataList.add(MypageMyReviewData("동대문엽기떡볶이 수유점", "2017-11-3", "ㅏㅏ"))

        mypageMyReviewAdapter = MypageMyReviewAdapter(activity!!, dataList)
        rv_review_myreview.adapter = mypageMyReviewAdapter
        rv_review_myreview.layoutManager = LinearLayoutManager(activity)
    }

}