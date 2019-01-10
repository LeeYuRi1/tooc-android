package com.tooc.android.tooc.join

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tooc.android.tooc.MainActivity
import com.tooc.android.tooc.R
import com.tooc.android.tooc.adapter.LuggagePictureAdapter
import com.tooc.android.tooc.dialog.MapChoiceDialog
import com.tooc.android.tooc.dialog.ReserveCancelDialog
import com.tooc.android.tooc.map.MapMorePreviewFragment
import com.tooc.android.tooc.model.reservation.ReservationReserveCodeData
import com.tooc.android.tooc.model.reservation.bagDtosData
import com.tooc.android.tooc.model.reservation.bagImgDtos
import com.tooc.android.tooc.network.ApplicationController
import com.tooc.android.tooc.network.NetworkService
import com.tooc.android.tooc.reserve.NoReserveFragment
import com.tooc.android.tooc.reserve_state.ReserveStateFragment
import kotlinx.android.synthetic.main.fragment_recentstore_detail.*
import kotlinx.android.synthetic.main.fragment_recentstore_detail.view.*
import kotlinx.android.synthetic.main.fragment_reserve_state.*
import kotlinx.android.synthetic.main.fragment_reserve_state.view.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat

class RecentstoreDetailFragment : Fragment(), OnMapReadyCallback {
    lateinit var networkService: NetworkService
    var reserveIdx: Int = 0
    lateinit var v: View

    private lateinit var mMap3: GoogleMap
    private lateinit var mapView3: MapView
    private lateinit var mGoogleApiClient: GoogleApiClient

    var latitude3: Double = 0.0
    var longitude3: Double = 0.0
    lateinit var qrCode: String
    //  var latitude: Double = 0.0
    //  var longitude: Double = 0.0

    // Google API Client 생성
    private lateinit var mLocation2: Location
    private lateinit var locationManager2: LocationManager
    private lateinit var mLocationRequest2: LocationRequest

    private lateinit var fusedLocationProviderClient2: FusedLocationProviderClient
    private lateinit var locationRequest2: LocationRequest
    private lateinit var locationCallback2: MapMorePreviewFragment.MyLocationCallBack2

    companion object {
        var mInstance: ReserveStateFragment? = null
        @Synchronized
        fun getInstance(): ReserveStateFragment {
            if (mInstance == null) {
                mInstance = ReserveStateFragment()
            }
            return mInstance!!
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(context)
        mMap3 = googleMap
        getgetStoreResponse()
        if (ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap3.isMyLocationEnabled = true
            mMap3.uiSettings.isMyLocationButtonEnabled = true
            mMap3.uiSettings.isCompassEnabled = true
            mMap3.uiSettings.isZoomGesturesEnabled = true
        }
    }

    var marker = LatLng(0.0, 0.0)
    fun setGoogleMap(shopName: String, latitude: Double, longitude: Double) {
        marker = LatLng(latitude, longitude)
        Log.d("TAGGG", "lat = " + latitude + "  long = " + longitude)
        mMap3.addMarker(MarkerOptions().position(marker).title(shopName).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_color_pin)))
//        mMap3.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 17f))
    }

    lateinit var luggagePictureAdapter: LuggagePictureAdapter

    var stateType: String? = null
    var reserveCode: String? = null
    var startTime: Long? = null
    var endTime: Long? = null
    var bagDtos = ArrayList<bagDtosData>()

    var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGoogleApiClient = GoogleApiClient.Builder(activity!!)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient.connect()

        arguments?.let { password = it.getString("password") }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_recentstore_detail, container, false)

//        getReservationReserveResponse(v)
        networkService = ApplicationController.instance.networkService

        var args:Bundle?=arguments
        reserveIdx = args!!.getInt("reserveIdx")
        Log.d("TAGGGGGGGGGGGGGGGGGGG","reserveIdx !!="+reserveIdx + "^^^"+args!!.getInt("reserveIdx"))

        mapView3 = v.findViewById(R.id.iv_store_map_recentdetail)
        mapView3.onCreate(savedInstanceState)
        mapView3.getMapAsync(this)

        v.iv_store_guide_recentdetail_find_road.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(activity!!,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(activity!!,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                var mLastKnownLocation: Location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
                latitude3 = mLastKnownLocation.latitude
                longitude3 = mLastKnownLocation.longitude
            }

            val mapChoiceDialog2: MapChoiceDialog = MapChoiceDialog(activity, latitude3, longitude3)
            mapChoiceDialog2.window.setGravity(Gravity.BOTTOM)
            mapChoiceDialog2.show()
        }
        return v
    }

    override fun onStart() {
        super.onStart()
        mapView3.onStart()
    }

    override fun onStop() {
        super.onStop()

        if (mGoogleApiClient.isConnected) {
            mGoogleApiClient.disconnect()
        }
    }

    //통신
    fun getgetStoreResponse() {
        var jwt = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        var getStoreResponse = networkService.getRecentReservationReserveResponse(jwt, reserveIdx)
        getStoreResponse.enqueue(object : Callback<ReservationReserveCodeData> {
            override fun onFailure(call: Call<ReservationReserveCodeData>, t: Throwable) {
                toast("onFailure")
            }

            override fun onResponse(call: Call<ReservationReserveCodeData>, response: Response<ReservationReserveCodeData>) {
                response?.let {
                    when (it?.code()) {
                        200 -> {
                            stateType = response.body()!!.stateType.toString()
                            reserveCode = response.body()!!.reserveCode.toString()
                            startTime = response.body()!!.startTime
                            endTime = response.body()!!.endTime
                            bagDtos = response.body()!!.bagDtos
                            var latitude = response.body()!!.store.latitude
                            var longitude = response.body()!!.store.longitude
//                            if (stateType == "RESERVED") {
//                                iv_circle_settlement_reservestate.setImageResource(R.drawable.ic_circle_empty)
//                                iv_circle_storage_reservestate.setImageResource(R.drawable.ic_circle_empty)
//                                iv_circle_collect_reservestate.setImageResource(R.drawable.ic_circle_empty)
//                                iv_payment_complete_reservestate.setImageResource(R.drawable.box_pay_no)
//                                btn_reservecancel_to_dialog.visibility = View.VISIBLE
//                                const_luggage_reservestate.visibility = View.GONE
//                            } else if (stateType == "PAYED") {
//                                iv_circle_settlement_reservestate.setImageResource(R.drawable.ic_circle_fill)
//                                iv_circle_storage_reservestate.setImageResource(R.drawable.ic_circle_empty)
//                                iv_circle_collect_reservestate.setImageResource(R.drawable.ic_circle_empty)
//                                iv_payment_complete_reservestate.setImageResource(R.drawable.box_pay_yes)
//                                btn_reservecancel_to_dialog.visibility = View.VISIBLE
//                                const_luggage_reservestate.visibility = View.GONE
//                            } else if (stateType == "ARCHIVE") {
//                                iv_circle_settlement_reservestate.setImageResource(R.drawable.ic_circle_fill)
//                                iv_circle_storage_reservestate.setImageResource(R.drawable.ic_circle_fill)
//                                iv_circle_collect_reservestate.setImageResource(R.drawable.ic_circle_empty)
//                                iv_payment_complete_reservestate.setImageResource(R.drawable.box_pay_yes)
//                                btn_reservecancel_to_dialog.visibility = View.GONE
//                                const_luggage_reservestate.visibility = View.VISIBLE
//
//                            } else if (stateType == "PICKUP") {
//                                iv_circle_settlement_reservestate.setImageResource(R.drawable.ic_circle_fill)
//                                iv_circle_storage_reservestate.setImageResource(R.drawable.ic_circle_fill)
//                                iv_circle_collect_reservestate.setImageResource(R.drawable.ic_circle_fill)
//                                iv_payment_complete_reservestate.setImageResource(R.drawable.box_pay_yes)
//                                btn_reservecancel_to_dialog.visibility = View.GONE
//                                const_luggage_reservestate.visibility = View.VISIBLE
//                            } else if (stateType == "CANCEL") {
//                                ReserveCancelDialog(ctx).show()
//                                //ReserveCancelPasswordConfirmDialog(ctx).show()
//                            }
                            //DATE
                            var dateTextFormat = SimpleDateFormat("yy년 MM월 dd일 EE요일")
                            tv_put_year_recentdetail.text = dateTextFormat.format(startTime)
                            tv_find_year_recentdetail.text = dateTextFormat.format(endTime)
                            //시간
                            var timeTextFormat = SimpleDateFormat("a hh시 mm분")
                            tv_put_ampm_recentdetail.text = timeTextFormat.format(startTime)
                            tv_find_ampm_recentdetail.text = timeTextFormat.format(endTime)
                            //위도경도
                            setGoogleMap(response.body()!!.store.storeName, latitude, longitude)
                            //bagDtos//TODO bagDtos해야함
                            Log.d("TAGG", "bagDtos : " + bagDtos.toString())
                            var final_priceUnit = response.body()!!.priceUnit + response.body()!!.extraChargeCount * response.body()!!.extraCharge
                            var total_amount: Int = 0
                            for (i in 0 until response.body()!!.bagDtos.size) {
                                Log.d("TAGGG", "bagDtos = " + response.body()!!.bagDtos[i].bagType)
                                if (response.body()!!.bagDtos[i].bagType == "CARRIER") {
                                    total_amount += response.body()!!.bagDtos[i].bagCount
                                    tv_carrier_num_recentdetail.text = response.body()!!.bagDtos[i].bagCount.toString()
                                    tv_carrier_money_recentdetail.text = (final_priceUnit * response.body()!!.bagDtos[i].bagCount).toString()
                                } else {
                                    total_amount += response.body()!!.bagDtos[i].bagCount
                                    tv_bag_num_recentdetail.text = response.body()!!.bagDtos[i].bagCount.toString()
                                    tv_bag_money_recentdetail.text = (final_priceUnit * response.body()!!.bagDtos[i].bagCount).toString()
                                }
                                mMap3.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 17f))

                                tv_total_num_recentdetail.text = total_amount.toString()
                                tv_total_money_recentdetail.text = (final_priceUnit * total_amount).toString()
                                tv_payment_amount_recentdetail.text = (total_amount * response.body()!!.price).toString()

                                var dataList: ArrayList<bagImgDtos> = response.body()!!.bagImgDtos

                                luggagePictureAdapter = LuggagePictureAdapter(activity!!, dataList)
                                rv_luggage_picture_recentdetail.adapter = luggagePictureAdapter
                                var mLayoutManager = LinearLayoutManager(context)
                                mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                                rv_luggage_picture_recentdetail.layoutManager = mLayoutManager

                                tv_store_name_recentdetail.text = response.body()!!.store.storeName
                                tv_store_location_recentdetail.text = response.body()!!.store.address
                            }
                            //상가정보 입력
                            tv_store_name_recentdetail.text = response.body()!!.store.storeName
                            tv_store_location_recentdetail.text = response.body()!!.store.address

                            var timeDateFormat = SimpleDateFormat("HH:mm")
                            var openTime: String = timeDateFormat.format(response.body()!!.store.openTime)
                            var closeTime: String = timeDateFormat.format(response.body()!!.store.closeTime)
                            tv_store_start_recentdetail.text = openTime
                            tv_store_end_recentdetail.text = closeTime

                            //총 시간                             textView112
                            var zero = 0
                            var allDateStamp = SimpleDateFormat("총 yy d일 HH시간 mm분")
                            var minTime = (endTime as Long - startTime as Long)
                            var allDay = minTime / 86400000
                            var allHour: Long
                            var allMinute: Long


                            if (allDay == zero.toLong()) {
                                allHour = (endTime as Long - startTime as Long) / 3600000
                                if (allHour == zero.toLong()) {
                                    allMinute = minTime / 60000
                                    textView1120.text = "총 " + allMinute + "분"
                                } else {
                                    var allMinute = (minTime - (allHour * 3600000)) / 60000
                                    if (allMinute == zero.toLong()) {
                                        textView1120.text = "총 " + allHour + "시간 "
                                    } else {
                                        textView1120.text = "총 " + allHour + "시간 " + allMinute + "분"
                                    }
                                }
                            } else {
                                allHour = (minTime - (allDay * 86400000)) / 3600000
                                if (allHour == zero.toLong()) {
                                    allMinute = (minTime - (allDay * 86400000)) / 60000
                                    if (allMinute == zero.toLong()) {
                                        textView1120.text = "총 " + allDay + "일"
                                    } else {
                                        textView1120.text = "총 " + allDay + "일 " + allMinute + "분"
                                    }
                                } else {
                                    allMinute = (minTime - (allDay * 86400000) - (allHour * 3600000)) / 60000
                                    if (allMinute == zero.toLong()) {
                                        textView1120.text = "총 " + allDay + "일 " + allHour + "시간 "
                                    } else {
                                        textView1120.text = "총 " + allDay + "일 " + allHour + "시간 " + allMinute + "분"
                                    }
                                }
                            }
                            var current_time: Long = System.currentTimeMillis()

                            var open_time: Long = response.body()!!.store.openTime.toLong()
                            var close_time: Long = response.body()!!.store.closeTime.toLong()

                            if ((Timestamp(open_time).hours < Timestamp(current_time).hours) && (Timestamp(current_time).hours < Timestamp(close_time).hours)) {
                                iv_open_close_sign.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
                            } else if (Timestamp(open_time).hours == Timestamp(current_time).hours) {//연시각과 현재시각이 같을때
                                if ((Timestamp(open_time).minutes <= Timestamp(current_time).minutes)) {  // 영업중
                                    iv_open_close_sign.setImageDrawable(resources.getDrawable(R.drawable.ic_working))

                                } else {
                                    iv_open_close_sign.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
                                }
                            } else if (Timestamp(close_time).hours == Timestamp(current_time).hours) {//닫는시각과 현재시각이 같을때
                                if ((Timestamp(close_time).minutes >= Timestamp(current_time).minutes)) {  // 영업중
                                    iv_open_close_sign.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
                                } else {
                                    iv_open_close_sign.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
                                }
                            } else {
                                iv_open_close_sign.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
                            }
                            Log.d("TAGGGG", "startTime = " + allDateStamp.format(startTime) + "  closeHour = " + allDateStamp.format(endTime))
                        }
                        400 -> {
                            (ctx as MainActivity).replaceFragment(NoReserveFragment())
                            SharedPreferencesController.instance!!.setPrefData("is_reserve", false)
                        }
                        500 -> {
                            toast("500 error")
                        }
                        else -> {
                            toast("error")

                            Log.d("TAGG", "reserveStateFragment code = " + response.code().toString())
                        }
                    }
                }
            }
        })

    }

}
