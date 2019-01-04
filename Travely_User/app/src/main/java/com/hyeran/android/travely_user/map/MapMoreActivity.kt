package com.hyeran.android.travely_user.map

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.LinearLayout
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.PhotoRecylerViewAdapter
import com.hyeran.android.travely_user.adapter.ReviewRecyclerViewAdapter
import com.hyeran.android.travely_user.data.PhotoData
import com.hyeran.android.travely_user.data.ReviewData
import com.hyeran.android.travely_user.dialog.MapChoiceDialog
import com.hyeran.android.travely_user.reserve.ReserveFragment
import kotlinx.android.synthetic.main.activity_map_more.*
import kotlinx.android.synthetic.main.fragment_map_more.*
import org.jetbrains.anko.activityManager
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast

class MapMoreActivity : AppCompatActivity() {
    lateinit var photoRecyclerViewAdapter: PhotoRecylerViewAdapter
    lateinit var reviewRecyclerViewAdapter: ReviewRecyclerViewAdapter

    var lng: Double = 0.0
    var lat: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_more)

        setRecyclerView()

        lat = intent.getDoubleExtra("lat", 0.0)
        lng = intent.getDoubleExtra("lng", 0.0)

//        toast(lat.toString() + "!@#!@#!@#!#" + lng.toString())

        setOnBtnClickListener()
    }


    private fun setOnBtnClickListener() {
        btn_map_more_act_find_road.setOnClickListener {
            val mapChoiceDialog: MapChoiceDialog = MapChoiceDialog(this, lat, lng)
            mapChoiceDialog.window.setGravity(Gravity.BOTTOM)
            mapChoiceDialog.show()
        }

        btn_map_more_act_reservation.setOnClickListener {
//            finish()
//            (activity as MainActivity).replaceFragment(ReserveFragment())
        }
    }

    private fun setRecyclerView() {
        // 임시 데이터 1
        val dataList1: ArrayList<PhotoData> = ArrayList()
        dataList1.add(PhotoData("filter1"))
        dataList1.add(PhotoData("filter2"))
        dataList1.add(PhotoData("filter3"))
        dataList1.add(PhotoData("filter4"))
        dataList1.add(PhotoData("filter5"))
        dataList1.add(PhotoData("filter6"))

        photoRecyclerViewAdapter = PhotoRecylerViewAdapter(this, dataList1)
        rv_map_more_act_photo_list.adapter = photoRecyclerViewAdapter
        rv_map_more_act_photo_list.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)

        // 임시 데이터 2
        val dataList2: ArrayList<ReviewData> = ArrayList()

        dataList2.add(ReviewData("reviewer", "김민수", 22, 4.5,
                "사장님이 굉장히 친절하고 덕분에 편안하게 여행했어요 최고~!!"))
        dataList2.add(ReviewData("reviewer", "이진원", 22, 4.5,
                "여행하는 중간에 짐이 너무 무거웠었는데, 짐을 맡길 수 있어서 너무 좋았어요!!"))
        dataList2.add(ReviewData("reviewer", "김민수", 22, 4.5,
                "사장님이 굉장히 친절하고 덕분에 편안하게 여행했어요 최고~!!"))
        dataList2.add(ReviewData("reviewer", "이진원", 22, 4.5,
                "여행하는 중간에 짐이 너무 무거웠었는데, 짐을 맡길 수 있어서 너무 좋았어요!!"))
        dataList2.add(ReviewData("reviewer", "김민수", 22, 4.5,
                "사장님이 굉장히 친절하고 덕분에 편안하게 여행했어요 최고~!!"))
        dataList2.add(ReviewData("reviewer", "이진원", 22, 4.5,
                "여행하는 중간에 짐이 너무 무거웠었는데, 짐을 맡길 수 있어서 너무 좋았어요!!"))

        reviewRecyclerViewAdapter = ReviewRecyclerViewAdapter(this, dataList2)
        rv_map_more_act_review_list.adapter = reviewRecyclerViewAdapter
        rv_map_more_act_review_list.layoutManager = LinearLayoutManager(this)
    }
}
