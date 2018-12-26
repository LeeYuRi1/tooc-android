package com.hyeran.android.travely_user

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import kotlinx.android.synthetic.main.fragment_map_more.*

class MapMoreFragment : Fragment() {
    lateinit var photoRecyclerViewAdapter : PhotoRecylerViewAdapter
    lateinit var reviewRecyclerViewAdapter : ReviewRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_map_more, container, false)

        return v
    }

    override fun onStart() {
        super.onStart()

        setRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setViewPager()
        setRecyclerView()
    }

    private fun setViewPager() {

    }

    private fun setRecyclerView() {
//        // 임시 데이터 1
//        val dataList1: ArrayList<PhotoData> = ArrayList()
//        dataList1.add(PhotoData("filter1"))
//        dataList1.add(PhotoData("filter2"))
//        dataList1.add(PhotoData("filter3"))
//        dataList1.add(PhotoData("filter4"))
//        dataList1.add(PhotoData("filter5"))
//        dataList1.add(PhotoData("filter6"))
//
//        photoRecyclerViewAdapter = PhotoRecylerViewAdapter(activity!!, dataList1)
//        rv_main_frag_photo_list.adapter = photoRecyclerViewAdapter
//        rv_main_frag_photo_list.layoutManager = GridLayoutManager(activity, 3)

        // 임시 데이터 2
        val dataList2: ArrayList<ReviewData> = ArrayList()

        dataList2.add(ReviewData("reviewer", "김민*", 22, 4.5,
                "사장님이 굉장히 친절하고 덕분에 편안하게 여행했어요 최고~!!"))
        dataList2.add(ReviewData("reviewer", "김민*", 22, 4.5,
                "사장님이 굉장히 친절하고 덕분에 편안하게 여행했어요 최고~!!"))
        dataList2.add(ReviewData("reviewer", "김민*", 22, 4.5,
                "사장님이 굉장히 친절하고 덕분에 편안하게 여행했어요 최고~!!"))

        reviewRecyclerViewAdapter = ReviewRecyclerViewAdapter(activity!!, dataList2)
        rv_map_more_frag_review_list.adapter = reviewRecyclerViewAdapter
        rv_map_more_frag_review_list.layoutManager = LinearLayoutManager(activity)
    }
}