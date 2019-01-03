package com.hyeran.android.travely_user.reserve

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reserve.view.*
import org.jetbrains.anko.support.v4.toast
import android.widget.CompoundButton
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.R.id.rb_kakaopay_reserve
import com.hyeran.android.travely_user.dialog.KeepPriceDialog
import com.hyeran.android.travely_user.dialog.ReserveCompleteDialog
import com.hyeran.android.travely_user.model.ReservationPriceListResponseData
import com.hyeran.android.travely_user.model.ErrorData
import com.hyeran.android.travely_user.model.reservation.ReservationSaveRequestData
import com.hyeran.android.travely_user.model.reservation.bagInfo
import com.hyeran.android.travely_user.model.store.StoreResponseData
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import com.hyeran.android.travely_user.util.SupportUtil
import kotlinx.android.synthetic.main.fragment_reserve.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.ctx
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class ReserveFragment : Fragment() {

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

    var afterParseStore: Long = 0
    var afterParseTake: Long = 0

    //        TODO("storeIdx를 받아서 통신해야함!!!!!!")
    var storeIdx: Int = 1

    var errorCheck: Boolean = false

    lateinit var networkService: NetworkService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_reserve, container, false)

        networkService = ApplicationController.instance.networkService

        getReservationPriceListResponse()

//        calPrice()

        //피커뷰와 데이터 통신을 하기 위한 코드
        var args: Bundle? = arguments

        //제한시간 받는 코드
        getStoreResponseInfo()

        var rightNow = Calendar.getInstance()
        var dateFormat = SimpleDateFormat("MMM dd일 (EE)")
        var dateParseFormat = SimpleDateFormat("yyyyMMM dd일 (EE) hh:mm")
        var yearDateFormat = SimpleDateFormat("yyyy")

        var defaultHourValue = rightNow.get(Calendar.HOUR_OF_DAY)
        var defaultMinuteValue = rightNow.get(Calendar.MINUTE)
        var presentYearValue = yearDateFormat.format(rightNow.time)

        //Store -> 피커에서 받은 데이터들을 뷰에 띄워줌
        v.tv_store_date_reserve.text = args?.getString("smmddee", dateFormat.format(rightNow.time))
        Log.d("TAGGG",(args?.getString("smmddee", dateFormat.format(rightNow.time))).toString())
        Log.d("TAGGGGG",dateFormat.format(rightNow.time))
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
//        if(afterParseStore < afterParseTake) {
//            toast(afterParseStore.toString() + "~~~" + afterParseTake)
//       }
        setOnClickListener(v)
        return v
    }

    fun setOnClickListener(v: View) {
        v.btn_alldate_reserve.setOnClickListener {
            var timeArray: ArrayList<Any> = arrayListOf(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm
                    , svalue, tvalue, openTime, closeTime)
            val dialog = ReserveTimeSettintDialog(ctx, timeArray)
            dialog.show()
        }

        v.btn_price_reserve.setOnClickListener {
            KeepPriceDialog(context).show()
        }

        //tv_result_amount_carrier_reserve, tv_result_amount_etc_reserve 뺌
        v.cb_carrier_reserve.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (isChecked) {
                    v.linear_carrier_more_reserve.visibility = View.VISIBLE
                    carrier_price = calPriceUnit(afterParseStore, afterParseTake)
                    v.tv_price_carrier_reserve.text = carrier_price.toString()
                    v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                    carrier_amount = 1
                    //v.tv_result_amount_carrier_reserve.text = carrier_amount.toString()

                    v.iv_carrier_amount_up_reserve.setOnClickListener {
                        carrier_amount = Integer.parseInt(v.tv_carrier_changing_amount_reserve.text as String?)
                        carrier_amount++
                        // v.tv_result_amount_carrier_reserve.text = carrier_amount.toString()
                        v.tv_carrier_changing_amount_reserve.text = carrier_amount.toString()
                        v.tv_carrier_amount_reserve.text = carrier_amount.toString()
                        carrier_price = carrier_amount * calPriceUnit(afterParseStore, afterParseTake)
                        v.tv_price_carrier_reserve.text = carrier_price.toString()
                        v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                    }
                    v.iv_carrier_amount_down_reserve.setOnClickListener {
                        carrier_amount = Integer.parseInt(v.tv_carrier_amount_reserve.text as String?)
                        if ((carrier_amount - 1) >= 0) {
                            carrier_amount--
                            // v.tv_result_amount_carrier_reserve.text = carrier_amount.toString()
                            v.tv_carrier_changing_amount_reserve.text = carrier_amount.toString()
                            v.tv_carrier_amount_reserve.text = carrier_amount.toString()
                            carrier_price = carrier_amount * calPriceUnit(afterParseStore, afterParseTake)
                            v.tv_price_carrier_reserve.text = carrier_price.toString()
                            v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                        }
                    }
                } else {
                    v.linear_carrier_more_reserve.visibility = View.GONE
                    carrier_price = 0
                    v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
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
                    v.tv_price_etc_reserve.text = etc_price.toString()
                    v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                    etc_amount = 1
                    // v.tv_result_amount_etc_reserve.text = etc_amount.toString()

                    v.iv_etc_amount_up_reserve.setOnClickListener {
                        etc_amount = Integer.parseInt(v.tv_etc_changing_amount_reserve.text as String?)
                        etc_amount++
                        v.tv_etc_changing_amount_reserve.text = etc_amount.toString()
                        v.tv_etc_amount_reserve.text = etc_amount.toString()
                        etc_price = etc_amount * calPriceUnit(afterParseStore, afterParseTake)
                        v.tv_price_etc_reserve.text = etc_price.toString()
                        v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                        //  v.tv_result_amount_etc_reserve.text = etc_amount.toString()
                    }
                    v.iv_etc_amount_down_reserve.setOnClickListener {
                        etc_amount = Integer.parseInt(v.tv_etc_amount_reserve.text as String?)
                        if ((etc_amount - 1) >= 0) {
                            etc_amount--
                            v.tv_etc_changing_amount_reserve.text = etc_amount.toString()
                            v.tv_etc_amount_reserve.text = etc_amount.toString()
                            etc_price = etc_amount * calPriceUnit(afterParseStore, afterParseTake)
                            v.tv_price_etc_reserve.text = etc_price.toString()
                            v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                            // v.tv_result_amount_etc_reserve.text = etc_amount.toString()
                        }
                    }
                } else {
                    etc_price = 0
                    v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                    v.linear_etc_more_reserve.visibility = View.GONE
                    etc_amount = 0
                    // v.tv_result_amount_etc_reserve.text = etc_amount.toString()
                }
            }
        })

        v.btn_reserve_reserve.setOnClickListener {


            if (smmddee != tmmddee || snumhh != tnumhh || snummm != tnummm) {

                if (v.rb_kakaopay_reserve.isChecked || v.rb_cash_reserve.isChecked) {

                    if (v.cb_carrier_reserve.isChecked || v.cb_etc_reserve.isChecked) {

                        if (v.cb_confirm_reserve.isChecked) {
                            //통신
                            postReserveInfo()

                            if (v.rb_kakaopay_reserve.isChecked) {
                                val intent = Intent(context, KakaopayWebView::class.java)
                                startActivityForResult(intent, 9999)
                            }
                            if (v.rb_cash_reserve.isChecked) {
                                ReserveCompleteDialog(context).show()
                            }

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

    fun postReserveInfo() {

        var reserveSave: ReservationSaveRequestData
        var bagData: ArrayList<bagInfo> = ArrayList()
        if (carrier_amount >= 1) {
            bagData.add(bagInfo("CARRIER", carrier_amount))
        } else if (etc_amount >= 1) {
            bagData.add(bagInfo("ETC", etc_amount))
        }
        if (rb_kakaopay_reserve.isChecked) {
            reserveSave = ReservationSaveRequestData(1, afterParseStore, afterParseTake, bagData, "CARD")
        } else {
            reserveSave = ReservationSaveRequestData(1, afterParseStore, afterParseTake, bagData, "CASH")
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
                            toast("예약 성공")
                            if (rb_kakaopay_reserve.isChecked) {
                                val intent: Intent = Intent(context, KakaopayWebView::class.java)
                                startActivityForResult(intent, 9999)
                            }
                            if (rb_cash_reserve.isChecked) {
                                ReserveCompleteDialog(context).show()
                            }
                            else{}
                        }
                        400 -> {
                            toast("잘못된 정보 주입")

                            if (response.errorBody() != null) {
                                var errorData: ErrorData = SupportUtil.getErrorMessage(response.errorBody()?.string())
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
                            Log.d("TAGGGGGGGGGGGGGGGGGG",it.code().toString())
                            if (response.errorBody() != null) {
                                var errorData: ErrorData = SupportUtil.getErrorMessage(response.errorBody()?.string())
                                //    toast("TAGG" + )
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

    fun getStoreResponseInfo() {
        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        var getStoreInfo = networkService.getStoreResponse(jwt, storeIdx)
        getStoreInfo.enqueue(object : Callback<StoreResponseData> {
            override fun onFailure(call: Call<StoreResponseData>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                errorCheck = true
            }

            override fun onResponse(call: Call<StoreResponseData>, response: Response<StoreResponseData>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            toast("200" + response.body()!!.closeTime.toString())
                            openTime = response.body()!!.openTime.toLong()
                            closeTime = response.body()!!.closeTime.toLong()
                        }
                        500 -> {
                            toast("500")
                            errorCheck = true
                        }
                        else -> {
                            toast("error")
                            errorCheck = true
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
                            toast("가격표 조회 성공")
//                            toast("@@1: "+response.body().toString())
                            priceArray = response.body()!!
//                            toast("@@2: "+priceArray.toString())

//                            calPrice()

                        }
                        500 -> {
                            toast("서버 에러")
                            errorCheck = true
                        }
                        else -> {
                            toast("error")
                            errorCheck = true
                        }
                    }
                }
            }

        })
    }

    fun calPriceUnit(afterParseStore : Long, afterParseTake : Long): Int {
        // 시간 계산
        var hour : Long = (afterParseTake - afterParseStore) / 1000 / 60 / 60
        if (hour * 1000 * 60 * 60 != afterParseTake - afterParseStore) {
            hour++
        }


        var price = 0

        for (i in 0..priceArray.size - 1) {

            if (priceArray.get(i).priceIdx < hour) {
                price = priceArray.get(i).price
            }
        }
        var extra_hour = 0

        var final_price = priceArray.get(priceArray.size - 1).priceIdx

        if (hour > final_price) {
            var temp_extra_hour = hour - final_price
            extra_hour = (temp_extra_hour / 12).toInt()
            if ((temp_extra_hour % 12L) == 0L) {
                extra_hour--
            }
        }

        var price_unit : Int = price + extra_hour * final_price

        return price_unit

    }
//
//    fun calPrice() {
//
//
//        var luggage_cnt = 3
//
//
//        var total_price : Long  = ((price + extra_hour * final_price) * luggage_cnt).toLong()
//
//        Log.d("가격 계산", total_price.toString())
//        Log.d("hour", hour.toString())
//        Log.d("price", price.toString())
//        Log.d("extra_hour", extra_hour.toString())
//        Log.d("final_price", final_price.toString())
//        Log.d("luggage_cnt", luggage_cnt.toString())
//    }

}
