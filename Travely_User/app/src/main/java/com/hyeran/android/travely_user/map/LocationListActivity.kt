package com.hyeran.android.travely_user.map

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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

    val dataList: ArrayList<RegionResponseData> by lazy {
        ArrayList<RegionResponseData>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_list)

        init()
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

    // 세부 정보 조회 함수
    private fun getStoreResponse() {

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        val getStoreResponse = networkService.getStoreResponse(jwt, 1)

        getStoreResponse!!.enqueue(object : Callback<StoreResponseData> {
            override fun onFailure(call: Call<StoreResponseData>, t: Throwable) {
            }

            override fun onResponse(call: Call<StoreResponseData>, response: Response<StoreResponseData>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            toast("세부정보 조회 성공")
                            toast(response.body().toString())

                        }
                        500 -> {
                            toast("서버 에러")
                        }
                        else -> {
                            toast("error"+it.code())
                        }
                    }
                }
            }

        })
    }

    // 예약 취소 함수
//    private fun postReservationCancelResponse() {
//
//        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
//        val postReservationCancelResponse = networkService.postReservationCancelResponse(jwt)
//
//        postReservationCancelResponse!!.enqueue(object : Callback<Any> {
//            override fun onFailure(call: Call<Any>, t: Throwable) {
//                Log.d("예약 취소", "#####"+t.message)
//            }
//
//            override fun onResponse(call: Call<Any>, response: Response<Any>) {
//                response?.let {
//                    when (it.code()) {
//                        200 -> {
//                            toast("예약 취소 성공 / 예약 내역 없음")
//                            toast(response.body().toString())
//                        }
//                        500 -> {
//                            toast("서버 에러")
//                        }
//                        else -> {
//                            toast("error"+it.code())
//                        }
//                    }
//                }
//            }
//
//        })
//    }

    // dd
}
