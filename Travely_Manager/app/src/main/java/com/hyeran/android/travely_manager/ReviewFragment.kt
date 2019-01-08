package com.hyeran.android.travely_manager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_review.view.*

/*
class ReviewFragment : Fragment() {

    lateinit var reviewListRVAdapter: ReviewListRVAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_review, container, false)

        var dataList : ArrayList<ReviewListTempData> = ArrayList()
        dataList.add(ReviewListTempData(0, "박상영", 22, 4.5, "사장님이 친절하시군요"))
        dataList.add(ReviewListTempData(0, "신혜란", 22, 4.5, "사장님이 굉장히 친절하고 덕분에 편안하게 여행했어요 최고~!!!"))

        reviewListRVAdapter = ReviewListRVAdapter(context, dataList)
        v.rv_review_list.adapter = reviewListRVAdapter
        v.rv_review_list.layoutManager = LinearLayoutManager(context)

        return v
    }
}
*/