package com.hyeran.android.travely_user.map

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.LocationRVAdapter
import com.hyeran.android.travely_user.model.region.RegionResponseData
import com.hyeran.android.travely_user.model.store.StoreResponseData
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.activity_location_list.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationListActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService

    lateinit var locationRVAdapter: LocationRVAdapter

    var storeIdx: Int = 0

    val dataList: ArrayList<RegionResponseData> by lazy {
        ArrayList<RegionResponseData>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_list)

        btn_back_location_list.setOnClickListener {
            setResult(111)
            finish()
        }

        btn_map_location_list.setOnClickListener {
            var intent = Intent()
            intent.putExtra("storeIdx", storeIdx)
            setResult(1, intent)
            finish()
        }

        init()
    }

    fun getStoreIdx(storeIdx : Int) {

        this.storeIdx = storeIdx

        var intent = Intent()
        intent.putExtra("storeIdx", storeIdx)
        setResult(2, intent)
        finish()
    }

    private fun init() {
        networkService = ApplicationController.instance.networkService
        setRecyclerview()
        getRegionResponse()
//        postReservationCancelResponse()
    }

    fun setRecyclerview() {

        locationRVAdapter = LocationRVAdapter(this, dataList)
        rv_by_area_temp.adapter = locationRVAdapter
        rv_by_area_temp.layoutManager = LinearLayoutManager(this)

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
