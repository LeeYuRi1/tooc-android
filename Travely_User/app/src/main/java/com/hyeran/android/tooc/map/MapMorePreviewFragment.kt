package com.hyeran.android.tooc.map

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hyeran.android.tooc.MainActivity
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.model.store.StoreResponseData
import com.hyeran.android.tooc.network.ApplicationController
import com.hyeran.android.tooc.network.NetworkService
import com.hyeran.android.tooc.reserve.ReserveFragment
import kotlinx.android.synthetic.main.fragment_map_more_preview.*
import kotlinx.android.synthetic.main.fragment_map_more_preview.view.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp

class MapMorePreviewFragment : Fragment(), OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    var latitude2: Double? = 0.0
    var longtitude2: Double? = 0.0

    var shop_latitude: Double = 0.0
    var shop_longitude: Double = 0.0
    var shop_name: String = ""

    var isAvailable = true

    override fun onConnected(bundle: Bundle?) {
        if (ActivityCompat.checkSelfPermission(activity!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity!!,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return
        }

        startLocationUpdates2()

        mLocation2 = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient2)
        if (mLocation2 == null) {
            startLocationUpdates2()
        }
        if (mLocation2 != null) {
            latitude2 = mLocation2.latitude
            longtitude2 = mLocation2.longitude
        } else {

        }
    }

    private fun startLocationUpdates2() {
        mLocationRequest2 = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        if (ActivityCompat.checkSelfPermission(activity!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity!!,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient2, mLocationRequest2, this)
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.i(TAG2, "Connection Suspended")
        mGoogleApiClient2.connect()
    }

    override fun onConnectionFailed(connectResult: ConnectionResult) {
        Log.i(TAG2, "Connection failed. Error : " + connectResult.errorCode)
    }

    override fun onStart() {
        super.onStart()

        mGoogleApiClient2.connect()
    }

    override fun onStop() {
        super.onStop()

        if (mGoogleApiClient2.isConnected) {
            mGoogleApiClient2.disconnect()
        }
    }

    override fun onLocationChanged(p0: Location?) {}

    private lateinit var mMap2: GoogleMap
    private lateinit var mapView2: MapView

    private lateinit var fusedLocationProviderClient2: FusedLocationProviderClient
    private lateinit var locationRequest2: LocationRequest
    private lateinit var locationCallback2: MapMorePreviewFragment.MyLocationCallBack2

    private val REQUEST_ACCESS_FINE_LOCATION2 = 1000

    // Google API Client 생성
    private lateinit var mGoogleApiClient2: GoogleApiClient
    private lateinit var mLocation2: Location
    private lateinit var locationManager2: LocationManager
    private lateinit var mLocationRequest2: LocationRequest

    private val TAG2 = javaClass.simpleName

    private var locationPermissionGranted2: Boolean = false


    private lateinit var lastLocation2: Location

    companion object {
        var mInstance2: MapMorePreviewFragment? = null
        @Synchronized
        fun getInstance(tsmmddee: String, tshh: Int, tsmm: Int, ttmmddee: String, tthh: Int, ttmm: Int, tsValue: Int, ttValue: Int): MapMorePreviewFragment {
            if (mInstance2 == null) {
                mInstance2 = MapMorePreviewFragment().apply {
                    arguments = Bundle().apply {
                        putString("smmddee", tsmmddee)
                        putInt("shh", tshh)
                        putInt("smm", tsmm)
                        putString("tmmddee", ttmmddee)
                        putInt("thh", tthh)
                        putInt("tmm", ttmm)
                        putInt("svalue", tsValue)
                        putInt("tvalue", ttValue)

                        Log.d("TAG", tsmmddee + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
                    }
                }
            }
            return mInstance2!!
        }
    }

    var storeIdx: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view2 = inflater.inflate(R.layout.fragment_map_more_preview, container, false)

        mapView2 = view2.findViewById(R.id.mapView2)
        mapView2.onCreate(savedInstanceState)
        mapView2.getMapAsync(this)

        var bundle: Bundle? = arguments
        storeIdx = bundle!!.getInt("storeIdx")

        toast(storeIdx.toString())

        getStoreResponse()

        view2.btn_fragment_map_question2.setOnClickListener {
            startActivityForResult<LocationListActivity>(999)
        }

        view2.btn_fragment_map_more_preview.setOnClickListener {
            var intent = Intent(activity, MapMoreActivity::class.java)
            if (ActivityCompat.checkSelfPermission(activity!!,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(activity!!,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                var mLastKnownLocation: Location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient2)
                latitude2 = mLastKnownLocation.latitude
                longtitude2 = mLastKnownLocation.longitude
            }

            intent.putExtra("lat", latitude2)
            intent.putExtra("lng", longtitude2)

            intent.putExtra("storeIdx", storeIdx)
            startActivityForResult(intent, 888)
        }

        view2.iv_reserve_map_more_preview.setOnClickListener {
            //            var reserve = SharedPreferencesController.instance!!.getPrefBooleanData("is_reserve")
            getStoreReserve()
//            if (isAvailable == false) {
//                toast("더 이상 해당 상가의 예약이 불가능합니다.")
//            } else {
//                if (reserve == false) {
//                    var args: Bundle = Bundle()
//                    var fragment: Fragment = ReserveFragment()
//                    args.putInt("storeIdx", storeIdx)
//                    fragment.arguments = args
//
//                    (activity as MainActivity).replaceFragment(fragment)
//                } else {
//                    toast("이미 예약 내역이 존재합니다.")
//                }
//            }
        }

        return view2
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locationInit2()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 999) {
            if (resultCode == 111) {
                (context as MainActivity).replaceFragment(MapFragment())
            } else {
                storeIdx = data!!.getIntExtra("storeIdx", 0)
                getStoreResponse()
            }
        }
        if (requestCode == 888) {
            if (resultCode == 777) {
                var storeIdx = data!!.getIntExtra("storeIdx", 0)
                var args = Bundle()
                args.putInt("storeIdx", storeIdx)
                var fragment: Fragment = ReserveFragment()
                fragment.arguments = args
                (ctx as MainActivity).replaceFragment(fragment)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        toast("onCreate")

        mGoogleApiClient2 = GoogleApiClient.Builder(activity!!)
                .addApi(LocationServices.API)
//                .addConnectionCallbacks(activity!!)
//                .addOnConnectFailedListener(activity!!)
                .build()

        mGoogleApiClient2.connect()

//        Log.d("TAGGGGG", latitude2.toString() + "asdasdasdasd" + longtitude2.toString())


    }


    override fun onMapReady(googleMap: GoogleMap) {

        MapsInitializer.initialize(context)
        mMap2 = googleMap

//        mMap2.setMinZoomPreference(17f)
//        mMap2.setMaxZoomPreference(21f)

        if (ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap2.isMyLocationEnabled = true
            mMap2.uiSettings.isMyLocationButtonEnabled = true
            mMap2.uiSettings.isCompassEnabled = true
            mMap2.uiSettings.isZoomGesturesEnabled = true
        }
    }

    fun setGoogleMap() {
        var marker = LatLng(shop_latitude, shop_longitude)
//        val marker = LatLng()
        mMap2.addMarker(MarkerOptions().position(marker).title(shop_name).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)))
        mMap2.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 17f))
    }


    override fun onResume() {
        super.onResume()
        mapView2.onResume()

        addLocationListener2()

        // 권한 요청
        permissionCheck(cancel = {
            // 위치 정보가 필요한 이유 다이얼로그 표시
            showPermissionInfoDialog2()
        }, ok = {
            // 현재 위치를 주기적으로 요청 (권한이 필요한 부분)
            addLocationListener2()
        })
    }


    override fun onLowMemory() {
        super.onLowMemory()
        mapView2.onLowMemory()
    }


    override fun onDestroy() {
        super.onDestroy()
        mapView2.onDestroy()
    }

    // 위치 정보를 얻기 위한 각종 초기화
    private fun locationInit2() {
        fusedLocationProviderClient2 = FusedLocationProviderClient(activity!!)

        locationCallback2 = MyLocationCallBack2()

        locationRequest2 = LocationRequest()

        // GPS 우선
        locationRequest2.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        // 업데이트 인터벌
        // 위치 정보가 없을 때는 업데이트 안 함
        // 상황에 따라 짧아질 수 있음, 정확하지 않음
        // 다른 앱에서 짧은 인터벌로 위치 정보를 요청하면 짧아질 수 있음
        locationRequest2.interval = 10000

        // 정확함. 이것보다 짧은 업데이트는 하지 않음
        locationRequest2.fastestInterval = 5000
    }

    @SuppressLint("MissingPermission")
    private fun addLocationListener2() {
        fusedLocationProviderClient2.requestLocationUpdates(locationRequest2,
                locationCallback2,
                null)
    }

    inner class MyLocationCallBack2 : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location2 = locationResult?.lastLocation

            /*
            location2?.run {
                // 14 level로 확대하고 현재 위치로 카메라 이동
                val latLng = LatLng(latitude, longitude)
                mMap2.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
//                mMap2.animateCamera(CameraUpdateFactory.zoomTo())

                Log.d("MapMorePreviewFragment", "위도 : $latitude, 경도 : $longitude")
            }
            */
        }
    }

    private fun permissionCheck(cancel: () -> Unit, ok: () -> Unit) {
        // 위치 권한이 있는지 검사
        if (ContextCompat.checkSelfPermission(activity!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한이 허용되지 않음
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 이전에 권한을 한 번 거부한 적이 있는 경우에 실행할 함수
                cancel()
            } else {
                // 권한 요청
                ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION2)
            }
        } else {
            // 권한을 수락했을 때 실행할 함수
            ok()
        }
    }

    private fun showPermissionInfoDialog2() {
        alert("현재 위치 정보를 얻으려면 위치 권한이 필요합니다", "권한이 필요한 이유") {
            yesButton {
                // 권한 요청
                ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_ACCESS_FINE_LOCATION2)
            }
            noButton { }
        }.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted2 = false

        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION2 -> {
                if ((grantResults.isNotEmpty()
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // 권한 혀용됨
                    addLocationListener2()
                } else {
                    // 권한 거부
                    toast("권한 거부 됨")
                }
                return
            }
        }
    }

    override fun onPause() {
        super.onPause()

        removeLocationListener2()
    }

    private fun removeLocationListener2() {
        // 현재 위치 요청을 삭제
        fusedLocationProviderClient2.removeLocationUpdates(locationCallback2)
    }

    lateinit var networkService: NetworkService

    private fun init() {
        networkService = ApplicationController.instance.networkService
//        postReservationCancelResponse()
    }

//    fun addMarker(googleMap : GoogleMap, shop_latitude : Double, shop_longitude : Double, shop_name : String) {
//
//        MapsInitializer.initialize(context)
//        mMap2 = googleMap
//
////        mMap2.setMinZoomPreference(17f)
////        mMap2.setMaxZoomPreference(21f)
//
//        if (ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            mMap2.isMyLocationEnabled = true
//            mMap2.uiSettings.isMyLocationButtonEnabled = true
//            mMap2.uiSettings.isCompassEnabled = true
//            mMap2.uiSettings.isZoomGesturesEnabled = true
//        }
//
//
//        val marker = LatLng(shop_latitude, shop_longitude)
////        val marker = LatLng()
//        mMap2.addMarker(MarkerOptions().position(marker).title(shop_name).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)))
//        mMap2.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 17f))
//    }

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
                            shop_name = response.body()!!.storeName
                            shop_latitude = response.body()!!.latitude
                            shop_longitude = response.body()!!.longitude

                            setGoogleMap()

                            tv_store_name_map_more_preview.text = response.body()!!.storeName
                            tv_address_map_more_preview.text = response.body()!!.address

                            Log.d("TAGG", "In MapMorePreviewFragment tv_address_map_more_preview = " + tv_address_map_more_preview.text)

                            var open_time: Long = response.body()!!.openTime.toLong()

                            if (Timestamp(open_time).hours.toString().trim().length == 1) {
                                tv_opentime_hour_map_more_preview.text = "0" + Timestamp(open_time).hours.toString().trim()
                            } else {
                                tv_opentime_hour_map_more_preview.text = Timestamp(open_time).hours.toString().trim()
                            }

                            if (Timestamp(open_time).minutes.toString().trim().length == 1) {
                                tv_opentime_minute_map_more_preview.text = "0" + Timestamp(open_time).minutes.toString().trim()
                            } else {
                                tv_opentime_minute_map_more_preview.text = Timestamp(open_time).minutes.toString().trim()
                            }

                            var close_time: Long = response.body()!!.closeTime.toLong()
                            if (Timestamp(close_time).hours.toString().trim().length == 1) {
                                var close_hour = "0" + Timestamp(close_time).hours.toString().trim()
                                if (close_hour == "00") {
                                    tv_closetime_hour_map_more_preview.text = "24"
                                }
                            } else {
                                tv_closetime_hour_map_more_preview.text = Timestamp(close_time).hours.toString().trim()
                            }

                            if (Timestamp(close_time).minutes.toString().trim().length == 1) {
                                tv_closetime_minute_map_more_preview.text = "0" + Timestamp(close_time).minutes.toString().trim()
                            } else {
                                tv_closetime_minute_map_more_preview.text = Timestamp(close_time).minutes.toString().trim()
                            }

                            var current_time: Long = System.currentTimeMillis()

                            if ((Timestamp(open_time).hours < Timestamp(current_time).hours) && (Timestamp(current_time).hours < Timestamp(close_time).hours)) {
                                iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
                            } else if (Timestamp(open_time).hours == Timestamp(current_time).hours) {//연시각과 현재시각이 같을때
                                if ((Timestamp(open_time).minutes <= Timestamp(current_time).minutes)) {  // 영업중
                                    iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
                                    toast("##")

                                } else {
                                    iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
                                }
                            } else if (Timestamp(close_time).hours == Timestamp(current_time).hours) {//닫는시각과 현재시각이 같을때
                                if ((Timestamp(close_time).minutes >= Timestamp(current_time).minutes)) {  // 영업중
                                    iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
                                    toast("$$")

                                } else {
                                    iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
                                }
                            } else {
                                iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
                            }

                            Log.d("@@@영업중 시간: ", Timestamp(open_time).hours.toString() + "~" + Timestamp(close_time).hours.toString())
                            Log.d("@@@현재 시간: ", Timestamp(current_time).hours.toString())


//                            var current_time: Long = System.currentTimeMillis()
//
//                            Log.d("@@@영업중 시간: ", Timestamp(open_time).hours.toString() + "~" + Timestamp(close_time).hours.toString())
//                            Log.d("@@@현재 시간: ", Timestamp(current_time).hours.toString())
//                            if ((Timestamp(open_time).hours < Timestamp(current_time).hours) && (Timestamp(current_time).hours < Timestamp(close_time).hours)) {
//                                iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
//                            } else if (Timestamp(open_time).hours == Timestamp(current_time).hours) {
//                                if ((Timestamp(open_time).minutes <= Timestamp(current_time).minutes)) {  // 영업중
//                                    iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
//                                } else {
//                                    iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
//                                }
//                            } else if (Timestamp(close_time).hours == Timestamp(close_time).hours) {
//                                if ((Timestamp(close_time).minutes >= Timestamp(close_time).minutes)) {  // 영업중
//                                } else if (Timestamp(close_time).hours == Timestamp(current_time).hours) {
//                                    if ((Timestamp(close_time).minutes >= Timestamp(current_time).minutes)) {  // 영업중
//                                        iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
//                                    } else {
//                                        iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
//                                    }
//                                } else if (Timestamp(close_time).hours == Timestamp(current_time).hours) {
//                                    if ((Timestamp(close_time).minutes >= Timestamp(current_time).minutes)) {  // 영업중
//                                        iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_working))
//                                    } else {
//                                        iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
//                                    }
//                                } else {
//                                    iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
//                                }
//                            }
//                            else {
//                                iv_working_map_more_preview.setImageDrawable(resources.getDrawable(R.drawable.ic_not_working))
//                            }
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


    private fun getStoreReserve() {
        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        val getStoreResponse = networkService.getStoreResponse(jwt, storeIdx)
        getStoreResponse.enqueue(object : Callback<StoreResponseData> {
            override fun onFailure(call: Call<StoreResponseData>, t: Throwable) {
            }
            override fun onResponse(call: Call<StoreResponseData>, response: Response<StoreResponseData>) {
                response.let {
                    when (it.code()) {
                        200 -> {
                            isAvailable = response.body()!!.available != -1
                            var reserve = SharedPreferencesController.instance!!.getPrefBooleanData("is_reserve")

                            if (response.body()!!.currentBag < response.body()!!.limit) {

                                if (isAvailable == false) {
                                    toast("더 이상 해당 상가의 예약이 불가능합니다.")
                                } else {
                                    if (reserve == false) {
                                        var args: Bundle = Bundle()
                                        var fragment: Fragment = ReserveFragment()
                                        args.putInt("storeIdx", storeIdx)
                                        fragment.arguments = args

                                        (activity as MainActivity).replaceFragment(fragment)
                                    } else {
                                        toast("이미 예약 내역이 존재합니다.")
                                    }
                                }
                            } else {
                                Toast.makeText(ctx, "해당 상가는 예약 가능한 상태가 아닙니다.", Toast.LENGTH_LONG).show()
                            }

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
