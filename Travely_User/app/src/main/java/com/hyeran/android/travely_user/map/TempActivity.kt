package com.hyeran.android.travely_user.map

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.LocationRVAdapter
import com.hyeran.android.travely_user.data.LocationTempData
import kotlinx.android.synthetic.main.activity_temp.*
import kotlinx.android.synthetic.main.item_more_location_map.*

class TempActivity : AppCompatActivity() {

    lateinit var locationRVAdapter: LocationRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)

//        color_temp.backgroundColor()
        setRecyclerview()

    }

    fun setRecyclerview() {

        // 지역별 보관장소
        var dataList_by_area : ArrayList<LocationTempData> = ArrayList()
        dataList_by_area.add(LocationTempData("홍대입구역", 3))
        dataList_by_area.add(LocationTempData("혜화역", 1))
        dataList_by_area.add(LocationTempData("동대문역사문화공원역", 1))

        locationRVAdapter = LocationRVAdapter(this, dataList_by_area)
        rv_by_area_temp.adapter = locationRVAdapter
        rv_by_area_temp.layoutManager = LinearLayoutManager(this)


        // 가까운 보관장소
        var dataList_nearby: ArrayList<LocationTempData> = ArrayList()
        dataList_nearby.add(LocationTempData("홍대입구역", 3))
        dataList_nearby.add(LocationTempData("혜화역", 1))
        dataList_nearby.add(LocationTempData("동대문역사문화공원역", 1))

        locationRVAdapter = LocationRVAdapter(this, dataList_nearby)
        rv_nearby_temp.adapter = locationRVAdapter
        rv_nearby_temp.layoutManager = LinearLayoutManager(this)


        // 즐겨찾는 장소
        var dataList_favorite : ArrayList<LocationTempData> = ArrayList()
        dataList_favorite.add(LocationTempData("홍대입구역", 3))
        dataList_favorite.add(LocationTempData("혜화역", 1))
        dataList_favorite.add(LocationTempData("동대문역사문화공원역", 1))

        locationRVAdapter = LocationRVAdapter(this, dataList_favorite)
        rv_favorite_temp.adapter = locationRVAdapter
        rv_favorite_temp.layoutManager = LinearLayoutManager(this)
    }
}
