package com.tooc.android.tooc.reserve

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reserve.view.*
import org.jetbrains.anko.support.v4.toast
import android.widget.CompoundButton
import com.google.gson.JsonObject
import com.tooc.android.tooc.R
import com.tooc.android.tooc.dialog.BagSizeDialog
import com.tooc.android.tooc.dialog.KeepPriceDialog
import com.tooc.android.tooc.dialog.ReserveCompleteDialog
import com.tooc.android.tooc.model.reservation.ReservationPriceListResponseData
import com.tooc.android.tooc.model.ErrorData
import com.tooc.android.tooc.model.reservation.ReservationSaveRequestData
import com.tooc.android.tooc.model.reservation.bagInfo
import com.tooc.android.tooc.model.store.StoreResponseData
import com.tooc.android.tooc.network.ApplicationController
import com.tooc.android.tooc.network.NetworkService
import com.tooc.android.tooc.util.ErrorMessage
import kotlinx.android.synthetic.main.fragment_reserve.*
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReserveFragment : Fragment() {
var decimalFormat =DecimalFormat("###,###")

    var carrier_amount: Int = 0
    var etc_amount: Int = 0
    var carrier_price: Int = 0
    var etc_price: Int = 0

    var smmddee: String? = null
    var tmmddee: String? = null
    var svalue: Int = 0
    var tvalue: Int = 0
    var snumhh: Int = 0
    var snummm: Int = 0
    var tnumhh: Int = 0
    var tnummm: Int = 0
    var openTime: Long = 0
    var closeTime: Long = 0
    var offday = ArrayList<String>() //쉬는 요일

    var afterParseStore: Long = 0
    var afterParseTake: Long = 0

    // TODO("storeIdx를 받아서 통신해야함!!!!!!")
    var storeIdx: Int = 1

    var dateParseFormat = SimpleDateFormat("yyyyMMM dd일 (EE) HH:mm")

    var errorCheck: Boolean = false

    lateinit var networkService: NetworkService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_reserve, container, false)

        networkService = ApplicationController.instance.networkService

        var args: Bundle? = arguments
        storeIdx = args!!.getInt("storeIdx")

        getReservationPriceListResponse()

        //피커뷰와 데이터 통신을 하기 위한 코드

        //제한시간 받는 코드
        getStoreResponseInfo()

        var rightNow = Calendar.getInstance()
        var dateFormat = SimpleDateFormat("MMM dd일 (EE)")
        var dateParseFormat = SimpleDateFormat("yyyyMMM dd일 (EE) HH:mm")
        var yearDateFormat = SimpleDateFormat("yyyy")

        var defaultHourValue = rightNow.get(Calendar.HOUR_OF_DAY)
        var defaultMinuteValue = rightNow.get(Calendar.MINUTE)
        var presentYearValue = yearDateFormat.format(rightNow.time)

        //Store -> 피커에서 받은 데이터들을 뷰에 띄워줌
        v.tv_store_date_reserve.text = args?.getString("smmddee", dateFormat.format(rightNow.time))
        Log.d("TAGGG", (args?.getString("smmddee", dateFormat.format(rightNow.time))).toString())
        Log.d("TAGGGGG", dateFormat.format(rightNow.time))
        snumhh = args!!.getInt("shh", defaultHourValue)
        if (snumhh < 10) {
            v.tv_store_hour_reserve.text = "0" + snumhh.toString()
        } else v.tv_store_hour_reserve.text = snumhh.toString()
        snummm = args!!.getInt("smm", defaultMinuteValue)
        if (snummm < 10) {
            v.tv_store_minute_reserve.text = "0" + snummm.toString()
        } else v.tv_store_minute_reserve.text = snummm.toString()

        //Take -> 피커에서 받은 데이터들을 뷰에 띄워줌
        v.tv_take_date_reserve.text = args!!.getString("tmmddee", dateFormat.format(rightNow.time))
        tnumhh = args!!.getInt("thh", defaultHourValue)
        if (tnumhh < 10) {
            v.tv_take_hour_reserve.text = "0" + tnumhh.toString()
        } else v.tv_take_hour_reserve.text = tnumhh.toString()
        tnummm = args!!.getInt("tmm", defaultMinuteValue)
        if (tnummm < 10) {
            v.tv_take_minute_reserve.text = "0" + tnummm.toString()
        } else v.tv_take_minute_reserve.text = tnummm.toString()

        //Send String Date
        smmddee = v.tv_store_date_reserve.text.toString()
        tmmddee = v.tv_take_date_reserve.text.toString()
        svalue = args!!.getInt("svalue")
        tvalue = args!!.getInt("tvalue")

        //서버로 time값을 전달해주기 위한 작업
        afterParseStore = dateParseFormat.parse(presentYearValue.toString() + smmddee.toString() + " " + snumhh.toString() + ":" + snummm.toString()).time
        afterParseTake = dateParseFormat.parse(presentYearValue.toString() + tmmddee.toString() + " " + tnumhh.toString() + ":" + tnummm.toString()).time

        setOnClickListener(v)
        return v
    }
    fun setOnClickListener(v: View) {
        v.constraintLayout5.setOnClickListener {
            var timeArray: ArrayList<Any> = arrayListOf(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm
                    , svalue, tvalue, openTime, closeTime, storeIdx, 0)
//            val dialog = ReserveTimeSettingDialog(ctx, timeArray)
            val dialog = ReserveTimeSettintDialog(ctx, timeArray, offday)
            dialog.show()
        }

        v.constraintLayout6.setOnClickListener{
            var timeArray: ArrayList<Any> = arrayListOf(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm
                    , svalue, tvalue, openTime, closeTime, storeIdx, 1)
//            val dialog = ReserveTimeSettingDialog(ctx, timeArray)
            val dialog = ReserveTimeSettintDialog(ctx, timeArray, offday)
            dialog.show()
        }
        v.btn_price_reserve.setOnClickListener {
            KeepPriceDialog(context).show()
        }

        v.btn_bag_size_reserve.setOnClickListener {
            BagSizeDialog(context).show()
        }

        //tv_result_amount_carrier_reserve, tv_result_amount_etc_reserve 뺌
        v.cb_carrier_reserve.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (isChecked) {
                    v.linear_carrier_more_reserve.visibility = View.VISIBLE
                    carrier_price = calPriceUnit(afterParseStore, afterParseTake)
                    v.tv_price_carrier_reserve.text = decimalFormat.format(carrier_price).toString()
                    v.tv_total_price_reserve.text = decimalFormat.format(carrier_price + etc_price).toString()
                    carrier_amount = 1

                    v.iv_carrier_amount_up_reserve.setOnClickListener {
                        carrier_amount = Integer.parseInt(v.tv_carrier_changing_amount_reserve.text as String?)
                        carrier_amount++
                        v.tv_carrier_changing_amount_reserve.text = carrier_amount.toString()
                        v.tv_carrier_amount_reserve.text = carrier_amount.toString()
                        carrier_price = carrier_amount * calPriceUnit(afterParseStore, afterParseTake)
                        v.tv_price_carrier_reserve.text = decimalFormat.format(carrier_price).toString()
                        v.tv_total_price_reserve.text = decimalFormat.format(carrier_price + etc_price).toString()
                    }
                    v.iv_carrier_amount_down_reserve.setOnClickListener {
                        carrier_amount = Integer.parseInt(v.tv_carrier_amount_reserve.text as String?)
                        if ((carrier_amount - 1) >= 0) {
                            carrier_amount--
                            // v.tv_result_amount_carrier_reserve.text = carrier_amount.toString()
                            v.tv_carrier_changing_amount_reserve.text = carrier_amount.toString()
                            v.tv_carrier_amount_reserve.text = carrier_amount.toString()
                            carrier_price = carrier_amount * calPriceUnit(afterParseStore, afterParseTake)
                            v.tv_price_carrier_reserve.text = decimalFormat.format(carrier_price).toString()
                            v.tv_total_price_reserve.text = decimalFormat.format(carrier_price + etc_price).toString()
                        }
                    }
                } else {
                    v.linear_carrier_more_reserve.visibility = View.GONE
                    carrier_price = 0
                    v.tv_total_price_reserve.text = decimalFormat.format(carrier_price + etc_price).toString()
                    carrier_amount = 0
                    // v.tv_result_amount_carrier_reserve.text = carrier_amount.toString()
                }
            }
        })

        v.cb_etc_reserve.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (isChecked) {
                    v.linear_etc_more_reserve.visibility = View.VISIBLE
                    etc_price = calPriceUnit(afterParseStore, afterParseTake)
                    v.tv_price_etc_reserve.text = decimalFormat.format(etc_price).toString()
                    v.tv_total_price_reserve.text = decimalFormat.format(carrier_price + etc_price).toString()
                    etc_amount = 1
                    // v.tv_result_amount_etc_reserve.text = etc_amount.toString()

                    v.iv_etc_amount_up_reserve.setOnClickListener {
                        etc_amount = Integer.parseInt(v.tv_etc_changing_amount_reserve.text as String?)
                        etc_amount++
                        v.tv_etc_changing_amount_reserve.text = etc_amount.toString()
                        v.tv_etc_amount_reserve.text = etc_amount.toString()
                        etc_price = etc_amount * calPriceUnit(afterParseStore, afterParseTake)
                        v.tv_price_etc_reserve.text = decimalFormat.format(etc_price).toString()
                        v.tv_total_price_reserve.text = decimalFormat.format(carrier_price + etc_price).toString()
                        //  v.tv_result_amount_etc_reserve.text = etc_amount.toString()
                    }
                    v.iv_etc_amount_down_reserve.setOnClickListener {
                        etc_amount = Integer.parseInt(v.tv_etc_amount_reserve.text as String?)
                        if ((etc_amount - 1) >= 0) {
                            etc_amount--
                            v.tv_etc_changing_amount_reserve.text = etc_amount.toString()
                            v.tv_etc_amount_reserve.text = etc_amount.toString()
                            etc_price = etc_amount * calPriceUnit(afterParseStore, afterParseTake)
                            v.tv_price_etc_reserve.text = decimalFormat.format(etc_price).toString()
                            v.tv_total_price_reserve.text = decimalFormat.format(carrier_price + etc_price).toString()
                            // v.tv_result_amount_etc_reserve.text = etc_amount.toString()
                        }
                    }
                } else {
                    etc_price = 0
                    v.tv_total_price_reserve.text = decimalFormat.format(carrier_price + etc_price).toString()
                    v.linear_etc_more_reserve.visibility = View.GONE
                    etc_amount = 0
                    // v.tv_result_amount_etc_reserve.text = etc_amount.toString()
                }
            }
        })

        v.btn_reserve_reserve.setOnClickListener {

//            toast("맡기는시간 : " +dateParseFormat.format(afterParseStore) + "  찾는 시간 : "+dateParseFormat.format(afterParseTake))
            if (smmddee != tmmddee || snumhh != tnumhh || snummm != tnummm) {

                if (v.rb_kakaopay_reserve.isChecked || v.rb_cash_reserve.isChecked) {

                    if (v.cb_carrier_reserve.isChecked || v.cb_etc_reserve.isChecked) {

                        if (v.cb_confirm_reserve.isChecked) {
                            //통신
                            postReserveInfo()
                        } else {
                            toast("결제 동의를 체크해주세요.")
                        }
                    } else {
                        toast("짐 종류를 선택해주세요.")
                    }
                } else {
                    toast("결제 방법을 선택해주세요.")
                }
            } else {
                toast("날짜 및 시간을 선택해주세요.")
            }
        }
    }

    fun postReserveInfo() {//예약 상태 저장

        var reserveSave: ReservationSaveRequestData
        var bagData: ArrayList<bagInfo> = ArrayList()
        if (carrier_amount >= 1) {
            bagData.add(bagInfo("CARRIER", carrier_amount))
        }
        if (etc_amount >= 1) {
            bagData.add(bagInfo("ETC", etc_amount))
        }
        if (rb_kakaopay_reserve.isChecked) {
            reserveSave = ReservationSaveRequestData(storeIdx.toLong(), afterParseStore, afterParseTake, bagData, "CARD")
        } else {
            reserveSave = ReservationSaveRequestData(storeIdx.toLong(), afterParseStore, afterParseTake, bagData, "CASH")
        }
        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        var postReservationSaveResponse = networkService.postReservationSaveResponse("application/json", jwt, reserveSave)
        postReservationSaveResponse.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onFailure(call: retrofit2.Call<JsonObject>, t: Throwable) {
                toast(("fail"))
                Log.d("postReservation: ", "@@@" + t.message)
                errorCheck = true
            }

            override fun onResponse(call: retrofit2.Call<JsonObject>, response: Response<JsonObject>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            toast("200")
                        }
                        201 -> {
                            toast("예약되었습니다.")
                            if (rb_kakaopay_reserve.isChecked) {
                                val intent: Intent = Intent(context, KakaopayWebView::class.java)
                                startActivityForResult(intent, 9999)
                            }
                            if (rb_cash_reserve.isChecked) {
                                ReserveCompleteDialog(context).show()
                                SharedPreferencesController.instance!!.setPrefData("is_reserve", true)
                            } else {
                            }
                            Log.d("TAGG: 예약할 때", bagData.toString())
                        }
                        400 -> {

                            if (response.errorBody() != null) {
                                var errorData: ErrorData = ErrorMessage.getErrorMessage(response.errorBody()?.string())
                                //    toast("TAGG" + )
                                toast(errorData.message)
                            }
                            Log.v("TAGG", reserveSave.toString())
                            Log.v("TAGG@@@@@@@@@@@@@@@@2", response.errorBody()?.string())
                            errorCheck = true
                        }

                        //이미 예약했는데 예약취소안하고 했을시 500뜸
                        500 -> {
                            Log.v("TAGG", reserveSave.toString())
                            Log.v("TAGG", response.body().toString())
                            toast("서버 에러")
                        }
                        else -> {
                            toast("error")
                            errorCheck = true
                            toast("ErroerCheck=" + errorCheck.toString())
                            Log.d("TAGGGGGGGGGGGGGGGGGG", it.code().toString())
                            if (response.errorBody() != null) {
                                var errorData: ErrorData = ErrorMessage.getErrorMessage(response.errorBody()?.string())
                                toast(errorData.message)
                            }
                            Log.v("TAGG", reserveSave.toString())
                            Log.v("TAGG@@@@@@@@@@@@@@@@2", response.errorBody()?.string())
                        }
                    }
                }
            }
        })
    }

    fun getStoreResponseInfo() { //상가 세부정보 조회
        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        var getStoreInfo = networkService.getStoreResponse(jwt, storeIdx)
//        toast("In getStoreResponseInfo" + storeIdx)
        getStoreInfo.enqueue(object : Callback<StoreResponseData> {
            override fun onFailure(call: Call<StoreResponseData>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<StoreResponseData>, response: Response<StoreResponseData>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            openTime = response.body()!!.openTime.toLong()
                            closeTime = response.body()!!.closeTime.toLong()
                            for (i in 0..response.body()!!.restWeekResponseDtos.size - 1) {
                                if (response.body()!!.restWeekResponseDtos[i].week == 1) {
                                    offday.add("일")
                                }else if (response.body()!!.restWeekResponseDtos[i].week == 2) {
                                    offday.add("월")
                                } else if (response.body()!!.restWeekResponseDtos[i].week == 3) {
                                    offday.add("화")
                                } else if (response.body()!!.restWeekResponseDtos[i].week == 4) {
                                    offday.add("수")
                                } else if (response.body()!!.restWeekResponseDtos[i].week == 5) {
                                    offday.add("목")
                                } else if (response.body()!!.restWeekResponseDtos[i].week == 6) {
                                    offday.add("금")
                                } else if (response.body()!!.restWeekResponseDtos[i].week == 7) {
                                    offday.add("토")
                                }
                            }
                        }
                        500 -> {
                            toast("500")
                        }
                        else -> {
                            toast("error")
                        }
                    }
                }
            }
        })
    }

    lateinit var priceArray: ArrayList<ReservationPriceListResponseData>
    private fun getReservationPriceListResponse() {

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")

        val getReservationPriceListResponse = networkService.getReservationPriceListResponse(jwt)

        getReservationPriceListResponse!!.enqueue(object : Callback<ArrayList<ReservationPriceListResponseData>> {
            override fun onFailure(call: Call<ArrayList<ReservationPriceListResponseData>>, t: Throwable) {
                errorCheck = true
            }

            override fun onResponse(call: Call<ArrayList<ReservationPriceListResponseData>>, response: Response<ArrayList<ReservationPriceListResponseData>>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
//                            toast("가격표 조회 성공")
//                            toast("@@1: "+response.body().toString())
                            priceArray = response.body()!!
//                            toast("@@2: "+priceArray.toString())
//                            calPrice()
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

    fun calPriceUnit(afterParseStore: Long, afterParseTake: Long): Int {
        // 시간 계산
        var hour: Long = (afterParseTake - afterParseStore) / 1000 / 60 / 60
        if (hour * 1000 * 60 * 60 != afterParseTake - afterParseStore) {
            hour++
        }

        Log.d("@@@@@@@@@", "snumhh: " + snumhh.toString())


        var price = 0

        for (i in 0..priceArray.size - 1) {

            if (priceArray.get(i).priceIdx < hour) {
                price = priceArray.get(i).price
            }
        }
        var extra_hour = 0

        var final_price_index = priceArray.get(priceArray.size - 1).priceIdx

        if (hour > final_price_index) {
            var temp_extra_hour = hour - final_price_index
            extra_hour = (temp_extra_hour / 12).toInt()
            if ((temp_extra_hour % 12L) == 0L) {
                extra_hour--
            }
        }

        var extra_price = priceArray.get(0).price

        var price_unit: Int = price + extra_hour * extra_price


        return price_unit

    }
}
