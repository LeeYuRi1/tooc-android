package com.hyeran.android.travely_user.map

import android.content.Context
import android.content.Intent
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
import com.hyeran.android.travely_user.dialog.MapChoiceDialog
import com.hyeran.android.travely_user.model.store.StoreResponseData
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.activity_map_more.*
import org.jetbrains.anko.toast
import com.hyeran.android.travely_user.data.ReviewData
import com.hyeran.android.travely_user.reserve.ReserveFragment
import kotlinx.android.synthetic.main.fragment_map_more.*
import org.jetbrains.anko.activityManager
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp

class MapMoreActivity : AppCompatActivity() {
    lateinit var photoRecyclerViewAdapter: PhotoRecylerViewAdapter
    lateinit var reviewRecyclerViewAdapter: ReviewRecyclerViewAdapter

    lateinit var networkService: NetworkService

    var lng: Double = 0.0
    var lat: Double = 0.0

    var storeIdx : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_more)

        btn_map_more_act_favorite.isSelected = false

        storeIdx = intent.getIntExtra("storeIdx", 0)

        init()

        getStoreResponse()

        setRecyclerView()

        lat = intent.getDoubleExtra("lat", 0.0)
        lng = intent.getDoubleExtra("lng", 0.0)
//        toast(lat.toString() + "!@#!@#!@#!#" + lng.toString())
        setOnBtnClickListener()
    }


    private fun setOnBtnClickListener() {
        btn_map_more_act_find_road.setOnClickListener {
            val mapChoiceDialog = MapChoiceDialog(this, lat, lng)
            mapChoiceDialog.window.setGravity(Gravity.BOTTOM)
            mapChoiceDialog.show()
        }

        iv_reserve_map_more.setOnClickListener {
            var intent = Intent()
            intent.putExtra("storeIdx", storeIdx)
            setResult(777, intent)
            finish()
        }

        btn_map_more_act_favorite.setOnClickListener {
            btn_map_more_act_favorite.isSelected = !btn_map_more_act_favorite.isSelected
        }
    }

    private fun init() {
        networkService = ApplicationController.instance.networkService
    }

    private fun setRecyclerView() {
        // 임시 데이터 1
        val dataList1: ArrayList<PhotoData> = ArrayList()
        dataList1.add(PhotoData("img_default_big"))
        dataList1.add(PhotoData("img_default_big"))
        dataList1.add(PhotoData("img_default_big"))
        dataList1.add(PhotoData("img_default_big"))
        dataList1.add(PhotoData("img_default_big"))
        dataList1.add(PhotoData("img_default_big"))
    }

    // 세부 정보 조회 함수
    private fun getStoreResponse() {
        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        val getStoreResponse = networkService.getStoreResponse(jwt, storeIdx)
        getStoreResponse.enqueue(object : Callback<StoreResponseData> {
            override fun onFailure(call: Call<StoreResponseData>, t: Throwable) {
            }

            override fun onResponse(call: Call<StoreResponseData>, response: Response<StoreResponseData>) {
                response.let {
                    when (it.code()) {
                        200 -> {
                            tv_store_name_map_more.text = response.body()!!.storeName
                            tv_grade_map_more.text = response.body()!!.grade.toString()
                            tv_address_map_more.text = response.body()!!.address
                            tv_address_number_map_more.text = response.body()!!.addressNumber

                            var open_time : Long = response.body()!!.openTime.toLong()

                            if(Timestamp(open_time).hours.toString().trim().length == 1) {
                                tv_opentime_hour_map_more.text = "0"+Timestamp(open_time).hours.toString().trim()
                            } else {
                                tv_opentime_hour_map_more.text = Timestamp(open_time).hours.toString().trim()
                            }

                            if(Timestamp(open_time).minutes.toString().trim().length == 1) {
                                tv_opentime_minute_map_more.text = "0"+Timestamp(open_time).minutes.toString().trim()
                            } else {
                                tv_opentime_minute_map_more.text = Timestamp(open_time).minutes.toString().trim()
                            }

                            var close_time : Long = response.body()!!.closeTime.toLong()
                            if(Timestamp(close_time).hours.toString().trim().length == 1) {
                                tv_closetime_hour_map_more.text = "0"+Timestamp(close_time).hours.toString().trim()
                            } else {
                                tv_closetime_hour_map_more.text = Timestamp(close_time).hours.toString().trim()
                            }

                            if(Timestamp(close_time).minutes.toString().trim().length == 1) {
                                tv_closetime_minute_map_more.text = "0"+Timestamp(close_time).minutes.toString().trim()
                            } else {
                                tv_closetime_minute_map_more.text = Timestamp(close_time).minutes.toString().trim()
                            }

                            var current_time : Long = System.currentTimeMillis()

                            if((Timestamp(open_time).hours < Timestamp(current_time).hours)&&(Timestamp(current_time).hours < Timestamp(close_time).hours)) {
                                iv_working_map_more.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
                            }
                            else if(Timestamp(open_time).hours == Timestamp(current_time).hours) {
                                if((Timestamp(open_time).minutes <= Timestamp(current_time).minutes)) {  // 영업중
                                    iv_working_map_more.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
                                }
                                else {
                                    iv_working_map_more.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
                                }
                            }
                            else if(Timestamp(close_time).hours == Timestamp(current_time).hours) {
                                if((Timestamp(close_time).minutes >= Timestamp(current_time).minutes)) {  // 영업중
                                    iv_working_map_more.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
                                }
                                else {
                                    iv_working_map_more.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
                                }
                            }
                            else {
                                iv_working_map_more.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
                            }
                            tv_store_url_map_more.text = response.body()!!.storeUrl
                            tv_store_call_map_more.text = response.body()!!.storeCall
                        }
                        500 -> {
                            toast("서버 에러")
                        }
                        else -> {
                            toast("error" + it.code())
                        }
                    }
                }
            }
        })
    }
}
