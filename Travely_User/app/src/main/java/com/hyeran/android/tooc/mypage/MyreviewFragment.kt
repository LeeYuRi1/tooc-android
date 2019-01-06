package com.hyeran.android.tooc.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.tooc.MainActivity
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.adapter.MypageLikeAdapter
import com.hyeran.android.tooc.adapter.MypageMyReviewAdapter
import com.hyeran.android.tooc.data.MypageMyReviewData
import kotlinx.android.synthetic.main.fragment_like.*
import kotlinx.android.synthetic.main.fragment_myreview.*
import org.jetbrains.anko.support.v4.ctx

class MyreviewFragment:Fragment() {

    lateinit var mypageMyReviewAdapter: MypageMyReviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_myreview, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()

        iv_back_mypage.setOnClickListener {
            (ctx as MainActivity).replaceFragment(MypageFragment())
        }
    }

    private fun setRecyclerView() {
        var dataList: ArrayList<MypageMyReviewData> = ArrayList()
        dataList.add(MypageMyReviewData("동대문엽기떡볶이 홍대점", "서울시 마포구 샬라샬라", "2018","3","11", "위치가 역이랑 가까워서 좋았어요!\n건물도 깨끗했고, 상가 근처에 예쁜 카페가 많아서 구경할거리도 많더라구요!"))
        dataList.add(MypageMyReviewData("동대문엽기떡볶이 강남점", "서울시 마포구 샬라샬라2", "2017","1","23","감사합니다." ))
        dataList.add(MypageMyReviewData("동대문엽기떡볶이 수유점", "서울시 마포구 샬라샬라3", "2016","7","9", "ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ"))

        mypageMyReviewAdapter = MypageMyReviewAdapter(activity!!, dataList)
        rv_review_myreview.adapter = mypageMyReviewAdapter
        rv_review_myreview.layoutManager = LinearLayoutManager(activity)
    }

}