package com.tooc.android.tooc.map

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.LinearLayout
import com.tooc.android.tooc.R
import com.tooc.android.tooc.adapter.PhotoRecylerViewAdapter
import com.tooc.android.tooc.adapter.StoreReviewAdapter
import com.tooc.android.tooc.dialog.MapChoiceDialog
import com.tooc.android.tooc.model.mypage.ReviewLookupData
import com.tooc.android.tooc.model.store.StoreResponseData
import com.tooc.android.tooc.network.ApplicationController
import com.tooc.android.tooc.network.NetworkService
import kotlinx.android.synthetic.main.activity_map_more.*
import org.jetbrains.anko.toast
import com.tooc.android.tooc.model.mypage.StoreFavoriteResponseData
import com.tooc.android.tooc.model.store.ReviewResponseData
import com.tooc.android.tooc.model.store.StoreImageResponseData
import kotlinx.android.synthetic.main.fragment_myreview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp

class MapMoreActivity : AppCompatActivity() {
    lateinit var photoRecyclerViewAdapter: PhotoRecylerViewAdapter
    lateinit var storeReviewAdapter: StoreReviewAdapter

    lateinit var networkService: NetworkService

    var lng: Double = 0.0
    var lat: Double = 0.0

    var storeIdx: Int = 0

    val dataList: ArrayList<StoreImageResponseData> by lazy {
        ArrayList<StoreImageResponseData>()
    }

    //lateinit var reviewDataList: ArrayList<ReviewResponseData>
    val dataListreview : ArrayList<ReviewResponseData> by lazy {
        ArrayList<ReviewResponseData>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_more)

        storeIdx = intent.getIntExtra("storeIdx", 0)

        networkService = ApplicationController.instance.networkService

        init()

        setRecyclerView()
        getStoreResponse()

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
            var reserve = SharedPreferencesController.instance!!.getPrefBooleanData("is_reserve")

            if (reserve == false) {
                var intent = Intent()
                intent.putExtra("storeIdx", storeIdx)
                setResult(777, intent)
                finish()
            } else {
                toast("이미 예약 내역이 존재합니다.")
            }
        }

        btn_map_more_act_favorite.setOnClickListener {
            if (btn_map_more_act_favorite.isSelected == true) {
                putFavoriteResponse()
                btn_map_more_act_favorite.isSelected = false
            }else if(btn_map_more_act_favorite.isSelected == false){
                putFavoriteResponse()
                btn_map_more_act_favorite.isSelected = true
            }else {
            }

            }

    }

    private fun init() {
        networkService = ApplicationController.instance.networkService
    }

    private fun setRecyclerView() {
        photoRecyclerViewAdapter = PhotoRecylerViewAdapter(this, dataList)
        rv_map_more_act_photo_list.adapter = photoRecyclerViewAdapter
        rv_map_more_act_photo_list.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
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
                            //storeimage
                            var dataList_image: ArrayList<StoreImageResponseData> = response.body()!!.storeImageResponseDtos
                            if (dataList_image.size > 0) {
                                val position = photoRecyclerViewAdapter.itemCount
                                photoRecyclerViewAdapter.dataList.addAll(dataList_image)
                                photoRecyclerViewAdapter.notifyItemInserted(position)
                            } else {
                            }

                            tv_store_name_map_more.text = response.body()!!.storeName
                            tv_grade_map_more.text = response.body()!!.grade.toString()
                            tv_address_map_more.text = response.body()!!.address
                            tv_address_number_map_more.text = response.body()!!.addressNumber

                            var open_time: Long = response.body()!!.openTime.toLong()

                            if (Timestamp(open_time).hours.toString().trim().length == 1) {
                                tv_opentime_hour_map_more.text = "0" + Timestamp(open_time).hours.toString().trim()
                            } else {
                                tv_opentime_hour_map_more.text = Timestamp(open_time).hours.toString().trim()
                            }

                            if (Timestamp(open_time).minutes.toString().trim().length == 1) {
                                tv_opentime_minute_map_more.text = "0" + Timestamp(open_time).minutes.toString().trim()
                            } else {
                                tv_opentime_minute_map_more.text = Timestamp(open_time).minutes.toString().trim()
                            }

                            var close_time: Long = response.body()!!.closeTime.toLong()
                            if (Timestamp(close_time).hours.toString().trim().length == 1) {
                                var close_hour = "0" + Timestamp(close_time).hours.toString().trim()
                                if (close_hour == "00") {
                                    close_hour = "24"
                                    tv_closetime_hour_map_more.text = "24"
                                }
//                                var close_hour = "0" + Timestamp(close_time).hours.toString().trim()
//                                if(close_hour == "00") tv_closetime_hour_map_more.text = "24"
                            } else {
                                tv_closetime_hour_map_more.text = Timestamp(close_time).hours.toString().trim()
                            }

                            if (Timestamp(close_time).minutes.toString().trim().length == 1) {
                                tv_closetime_minute_map_more.text = "0" + Timestamp(close_time).minutes.toString().trim()
                            } else {
                                tv_closetime_minute_map_more.text = Timestamp(close_time).minutes.toString().trim()
                            }

                            var current_time: Long = System.currentTimeMillis()

                            if ((Timestamp(open_time).hours < Timestamp(current_time).hours) && (Timestamp(current_time).hours < close_time.toInt())) {
                                iv_working_map_more.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
                            } else if (Timestamp(open_time).hours == Timestamp(current_time).hours) {
                                if ((Timestamp(open_time).minutes <= Timestamp(current_time).minutes)) {  // 영업중
                                    iv_working_map_more.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
                                } else {
                                    iv_working_map_more.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
                                }
                            } else if (close_time.toInt() == Timestamp(current_time).hours) {
                                if ((Timestamp(close_time).minutes >= Timestamp(current_time).minutes)) {  // 영업중
                                    iv_working_map_more.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
                                } else {
                                    iv_working_map_more.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
                                }
                            } else {
                                iv_working_map_more.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
                            }
                            tv_store_url_map_more.text = response.body()!!.storeUrl
                            tv_store_call_map_more.text = response.body()!!.storeCall

//                            tv_hour_rv_item.text = dataListreview.size.toString()


                            //store 리뷰
                            storeReviewAdapter = StoreReviewAdapter(this@MapMoreActivity, dataListreview)
                            rv_map_more_act_review_list.adapter = storeReviewAdapter
                            rv_map_more_act_review_list.layoutManager = LinearLayoutManager(this@MapMoreActivity)


                            var dataList_review : ArrayList<ReviewResponseData> = response.body()!!.reviewResponseDtos
                            if (dataList_review.size > 0) {
                                val position = storeReviewAdapter.itemCount
                                storeReviewAdapter.dataList.addAll(dataList_review)
                                storeReviewAdapter.notifyItemInserted(position)

                            } else {

                            }

                            //favorite
                            if (response.body()!!.isFavorite == 1) {   //즐겨찾기됨
                                btn_map_more_act_favorite.isSelected = true

                            } else if (response.body()!!.isFavorite == -1) {   //즐겨찾기안됨
                                btn_map_more_act_favorite.isSelected = false

                            } else {
                            }


                        }
                        500 -> {
                        }
                        else -> {
                        }
                    }
                }
            }
        })
    }

    fun putFavoriteResponse() {

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")

        val putStoreFavoriteResponse = networkService.putStoreFavoriteResponse(jwt, storeIdx)

        putStoreFavoriteResponse!!.enqueue(object : Callback<StoreFavoriteResponseData> {
            override fun onFailure(call: Call<StoreFavoriteResponseData>, t: Throwable) {
            }

            override fun onResponse(call: Call<StoreFavoriteResponseData>, response: Response<StoreFavoriteResponseData>) {
                response?.let {
                    when (it.code()) {
                        200 -> {

                        }
                        400 -> {
                        }
                        500 -> {
                        }
                        else -> {
                        }
                    }
                }
            }
        })
    }


}
