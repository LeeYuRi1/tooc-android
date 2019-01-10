package com.hyeran.android.travely_manager


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reserve_detail.*
import android.util.Log
import com.bumptech.glide.Glide
import com.hyeran.android.travely_manager.db.SharedPreferencesController
import com.hyeran.android.travely_manager.model.BagDtos
import com.hyeran.android.travely_manager.model.ReserveDetailResponseData
import com.hyeran.android.travely_manager.network.ApplicationController
import com.hyeran.android.travely_manager.network.NetworkService
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class ReserveDetailFragment : Fragment() {


    lateinit var networkService : NetworkService

    lateinit var v : View

    var reserveCode : String = ""

    var pick_reserveIdx = 0.toLong()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_reserve_detail, container, false)

        networkService = ApplicationController.instance.networkService

        (ctx as MainActivity).selectedTabChangeColor(1)

//        v.btn_store_reserve_detail.setOnClickListener {
//
//            if(!cb_pay_agree.isChecked) {
//                CashInfoDialog(context).show()
//            }
//            else{
//                StorePhotoDialog(context).show()
//            }
//        }

        var bundle : Bundle? = arguments
        reserveCode = bundle!!.getString("reserveCode")

        toast(reserveCode)

        setClickListener()
        getStoreIdxReserveCodeResponse()

        return v
    }

    private fun setClickListener() {
        btn_storage_reservedetail.setOnClickListener {
            if(cb_confirm_reservedetail.isChecked){
                putStorePickupResponse()
                getStoreIdxReserveCodeResponse()
            }else{
                toast("동의해주세요")
            }
        }

        btn_pickup_reservedetail.setOnClickListener {
            putStorePickupResponse()
            getStoreIdxReserveCodeResponse()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == 999) {
//            if (resultCode == Activity.RESULT_OK) { // 이 두 부분은 형식적
//                data?.let {
//                    val image = data.extras.get("data") as Bitmap
//                    val imageview = v.findViewById(R.id.iv_test_reserve_detail) as ImageView //sets imageview as the bitmap
//                    imageview.setImageBitmap(image)
//                }
//            }
//        }
    }

    private fun getStoreIdxReserveCodeResponse() {

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        var storeIdx: Int = SharedPreferencesController.instance!!.getPrefIntegerData("storeIdx")

        val getStoreIdxReserveCodeResponse = networkService.getStoreIdxReserveCodeResponse(jwt, reserveCode, storeIdx)

        getStoreIdxReserveCodeResponse!!.enqueue(object : Callback<ReserveDetailResponseData> {
            override fun onFailure(call: Call<ReserveDetailResponseData>, t: Throwable) {
            }

            override fun onResponse(call: Call<ReserveDetailResponseData>, response: Response<ReserveDetailResponseData>) {
                Log.d("@@@@@@@@@@@", "reserveCode: "+reserveCode+", storeIdx: "+storeIdx)

                response?.let {
                    when (it.code()) {
                        200 -> {
                            toast("예약코드 조회 성공")
                            tv_name_reservedetail.text = response.body()!!.userName
                            tv_phone_reservedetail.text = response.body()!!.userPhone
                            if (response.body()!!.payType == "CASH") {
                                tv_payment_reservedetail.text = "현금"
                            }
                            else {
                                tv_payment_reservedetail.text = "카카오페이"
                            }
                            var bagDtoList_ : ArrayList<BagDtos> = response.body()!!.bagDtoList
                            var totalCnt = 0
                            var carrierCnt = 0
                            var bagCnt = 0


                            Log.d("@@@@@@@@@@@@@@@@TAG", bagDtoList_.toString())

                            for (i in 0 until bagDtoList_.size) {
                                if (bagDtoList_[i].bagType == "CARRIER") {
                                    totalCnt += bagDtoList_[i].bagCount.toInt()
                                    carrierCnt = bagDtoList_[i].bagCount.toInt()
                                    tv_carrier_num_reservedetail.text = carrierCnt.toString()
                                    Log.d("@@@@@@@@@@@@@CARRIER", bagDtoList_[i].bagCount.toInt().toString())
                                } else {
                                    totalCnt += bagDtoList_[i].bagCount.toInt()
                                    bagCnt = bagDtoList_[i].bagCount.toInt()
                                    tv_bag_num_reservedetail.text = bagCnt.toString()
                                    Log.d("@@@@@@@@@@@@@@BAG", bagDtoList_[i].bagCount.toInt().toString())
                                }
                            }
                            tv_num_reservedetail.text = totalCnt.toString()

                            var priceUnit = response.body()!!.price/totalCnt

                            tv_carrier_money_reservedetail.text = (priceUnit * carrierCnt).toString()
                            tv_bag_money_reservedetail.text = (priceUnit * bagCnt).toString()

                            tv_total_num_reservedetail.text = totalCnt.toString()
                            tv_total_money_reservedetail.text = priceUnit.toString()
                            tv_payment_amount_reservedetail.text = response.body()!!.price.toString()

                            if (response.body()!!.payType != "RESERVED") {
                                iv_no_payment_reservedetail.setImageResource(R.drawable.reserve_pay_rect_gray)
                                btn_picture_reservedetail.visibility = View.INVISIBLE
                            }

                            //(
                            var allDateFormat = SimpleDateFormat("yy년 MM월 dd일 E요일")
                            var allTimeFormat = SimpleDateFormat("aa HH시 mm분")
                            var StoreDateText = allDateFormat.format(response.body()!!.startTime)
                            var StoreTimeText = allTimeFormat.format(response.body()!!.startTime)
                            var TakeDateText = allDateFormat.format(response.body()!!.endTime)
                            var TakeTimeText = allTimeFormat.format(response.body()!!.endTime)

                            tv_startday_reservedetail.text = StoreDateText
                            tv_starttime_reservedetail.text = StoreTimeText
                            tv_endday_reservedetail.text = TakeDateText
                            tv_endtime_reservedetail.text = TakeTimeText

                            //총 시간                             textView112
                            var zero = 0
//                            var allDateStamp = SimpleDateFormat("총 yy d일 HH시간 mm분")
                            var minTime = (response.body()!!.endTime - response.body()!!.startTime)
                            var allDay = minTime / 86400000
                            var allHour: Long
                            var allMinute: Long


                            if (allDay == zero.toLong()) {
                                allHour = (response.body()!!.endTime - response.body()!!.startTime) / 3600000
                                if (allHour == zero.toLong()) {
                                    allMinute = minTime / 60000
                                    tv_total_day_reservedetail.text = allMinute.toString() + "분"
                                } else {
                                    var allMinute = (minTime - (allHour * 3600000)) / 60000
                                    if (allMinute == zero.toLong()) {
                                        tv_total_day_reservedetail.text = allHour.toString() + "시간 "
                                    } else {
                                        tv_total_day_reservedetail.text = allHour.toString() + "시간 " + allMinute + "분"
                                    }
                                }
                            } else {
                                allHour = (minTime - (allDay * 86400000)) / 3600000
                                if (allHour == zero.toLong()) {
                                    allMinute = (minTime - (allDay * 86400000)) / 60000
                                    if (allMinute == zero.toLong()) {
                                        tv_total_day_reservedetail.text = allDay.toString() + "일"
                                    } else {
                                        tv_total_day_reservedetail.text = allDay.toString() + "일 " + allMinute + "분"
                                    }
                                } else {
                                    allMinute = (minTime - (allDay * 86400000) - (allHour * 3600000)) / 60000
                                    if (allMinute == zero.toLong()) {
                                        tv_total_day_reservedetail.text = allDay.toString() + "일 " + allHour + "시간 "
                                    } else {
                                        tv_total_day_reservedetail.text = allDay.toString() + "일 " + allHour + "시간 " + allMinute + "분"
                                    }
                                }
                            }

                            Glide.with(this@ReserveDetailFragment)
                                    .load(response.body()!!.userImgUrl)
                                    .into(iv_profile_reservedetail)

                            //짐 보관, 픽업
                            pick_reserveIdx = response.body()!!.reserveIdx

                            if(response.body()!!.stateType == "RESERVED"){   //예약상태
                                layout_storage_reservedetail.visibility = View.VISIBLE
                                layout_pickup_reservedetail.visibility = View.GONE
                            } else if(response.body()!!.stateType == "ARCHIVE"){   //보관상태
                                layout_storage_reservedetail.visibility = View.GONE
                                layout_pickup_reservedetail.visibility = View.VISIBLE
//                            }else if(response.body()!!.stateType == "PICKUP"){
//                                layout_storage_reservedetail.visibility = View.GONE
//                                layout_pickup_reservedetail.visibility = View.VISIBLE
                            }else{

                            }


                        }
                        400 -> {
                            toast("예약 및 보관내역 없음")
                        }
                        403 -> {
                            toast("인증 에러")
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


    private fun putStorePickupResponse() {
        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")

        val putStorePickUpResponse = networkService.putStorePickUpResponse(jwt, pick_reserveIdx)

        putStorePickUpResponse!!.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response?.let {
                    when (it.code()) {
                        200 -> {

                            toast("짐 보관 및 픽업 성공")
                        }
                        401 -> {
                            toast("인증 에러")
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