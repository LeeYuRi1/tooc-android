package com.hyeran.android.travely_user.map

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.LocationRVAdapter
import com.hyeran.android.travely_user.model.RegionResponseData
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.activity_temp.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationListActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService

    lateinit var locationRVAdapter: LocationRVAdapter

    val dataList: ArrayList<RegionResponseData> by lazy {
        ArrayList<RegionResponseData>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)
//        color_temp.backgroundColor()

        init()
    }

    private fun init() {
        networkService = ApplicationController.instance.networkService
        setRecyclerview()
        getRegionResponse()
    }

    fun setRecyclerview() {

//        dataList_by_area.add(LocationTempData("홍대입구역", 3))
//        dataList_by_area.add(LocationTempData("혜화역", 1))
//        dataList_by_area.add(LocationTempData("동대문역사문화공원역", 1))

        locationRVAdapter = LocationRVAdapter(this, dataList)
        rv_by_area_temp.adapter = locationRVAdapter
        rv_by_area_temp.layoutManager = LinearLayoutManager(this)


//        // 가까운 보관장소
//        var dataList_nearby: ArrayList<LocationTempData> = ArrayList()
//        dataList_nearby.add(LocationTempData("홍대입구역", 3))
//        dataList_nearby.add(LocationTempData("혜화역", 1))
//        dataList_nearby.add(LocationTempData("동대문역사문화공원역", 1))
//
//        locationRVAdapter = LocationRVAdapter(this, dataList_nearby)
//        rv_nearby_temp.adapter = locationRVAdapter
//        rv_nearby_temp.layoutManager = LinearLayoutManager(this)
//
//
//        // 즐겨찾는 장소
//        var dataList_favorite : ArrayList<LocationTempData> = ArrayList()
//        dataList_favorite.add(LocationTempData("홍대입구역", 3))
//        dataList_favorite.add(LocationTempData("혜화역", 1))
//        dataList_favorite.add(LocationTempData("동대문역사문화공원역", 1))
//
//        locationRVAdapter = LocationRVAdapter(this, dataList_favorite)
//        rv_favorite_temp.adapter = locationRVAdapter
//        rv_favorite_temp.layoutManager = LinearLayoutManager(this)
    }

    private fun getRegionResponse() {

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")

        val getRegionResponse = networkService.getRegionResponse(jwt)

        getRegionResponse!!.enqueue(object : Callback<ArrayList<RegionResponseData>> {
            override fun onFailure(call: Call<ArrayList<RegionResponseData>>, t: Throwable) {
            }

            override fun onResponse(call: Call<ArrayList<RegionResponseData>>, response: Response<ArrayList<RegionResponseData>>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            toast("목록조회 성공")
                            toast(response.body().toString())

                            // 지역별 보관장소
                            var dataList_by_area: ArrayList<RegionResponseData> = response.body()!!

                            if (dataList_by_area.size > 0) {
                                val position = locationRVAdapter.itemCount
                                locationRVAdapter.dataList.addAll(dataList_by_area)
                                locationRVAdapter.notifyItemInserted(position)
                            } else {

                            }
                        }
                        500 -> {
                            toast("서버 에러")
                        }
                        else -> {
                            toast("error")
                        }
                    }
                }
            }

        })
    }
}
