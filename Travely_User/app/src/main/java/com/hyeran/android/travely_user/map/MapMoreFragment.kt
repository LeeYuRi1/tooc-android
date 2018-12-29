package com.hyeran.android.travely_user.map

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.data.ReviewData
import com.hyeran.android.travely_user.adapter.PhotoRecylerViewAdapter
import com.hyeran.android.travely_user.adapter.ReviewRecyclerViewAdapter
import com.hyeran.android.travely_user.data.PhotoData
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
//        setViewPager()
        setRecyclerView()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        button_button.setOnClickListener{
//            val showmeRoute = "daummaps://route?sp=37.537229,127.005515&ep=37.4979502,127.0276368&by=CAR"
//            val u = android.net.Uri.parse(showmeRoute)
//            i.setData(u)
//            startActivity(i)

//            val url : String = "daummaps://route?sp=37.537229,127.005515&ep=37.4979502,127.0276368&by=PUBLICTRANSIT"
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//            startActivity(intent)

            val url : String = "http://maps.google.com/maps?saddr=37.537229,127.005515&daddr=37.4979502,127.0276368"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.setPackage("com.google.android.apps.maps")

            startActivity(intent)
        }
    }

//    private fun setViewPager() {
//        var images : Array<Int> = arrayOf(R.drawable.filter1, R.drawable.filter2, R.drawable.filter3,
//                R.drawable.filter4, R.drawable.filter5, R.drawable.filter6)
//        var adapter : PagerAdapter = PhotoPagerAdapter(activity!!, images)
//        vp_map_more_frag_photo_list.adapter = adapter
//    }

    private fun setRecyclerView() {
        // 임시 데이터 1
        val dataList1: ArrayList<PhotoData> = ArrayList()
        dataList1.add(PhotoData("filter1"))
        dataList1.add(PhotoData("filter2"))
        dataList1.add(PhotoData("filter3"))
        dataList1.add(PhotoData("filter4"))
        dataList1.add(PhotoData("filter5"))
        dataList1.add(PhotoData("filter6"))

        photoRecyclerViewAdapter = PhotoRecylerViewAdapter(activity!!, dataList1)
        rv_map_more_frag_photo_list.adapter = photoRecyclerViewAdapter
        rv_map_more_frag_photo_list.layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)

        // 임시 데이터 2
        val dataList2: ArrayList<ReviewData> = ArrayList()

        dataList2.add(ReviewData("reviewer", "김민수", 22, 4.5,
                "사장님이 굉장히 친절하고 덕분에 편안하게 여행했어요 최고~!!"))
        dataList2.add(ReviewData("reviewer", "이진원", 22, 4.5,
                "여행하는 중간에 짐이 너무 무거웠었는데, 짐을 맡길 수 있어서 너무 좋았어요!!"))

        reviewRecyclerViewAdapter = ReviewRecyclerViewAdapter(activity!!, dataList2)
        rv_map_more_frag_review_list.adapter = reviewRecyclerViewAdapter
        rv_map_more_frag_review_list.layoutManager = LinearLayoutManager(activity)
    }
}