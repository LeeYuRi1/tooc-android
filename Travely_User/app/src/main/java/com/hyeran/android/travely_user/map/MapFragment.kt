package com.hyeran.android.travely_user.map

import android.annotation.SuppressLint
import android.content.Intent
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
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.R
import kotlinx.android.synthetic.main.fragment_map.view.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton


class MapFragment : Fragment(), OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    override fun onConnected(bundle: Bundle?) {
        if (ActivityCompat.checkSelfPermission(activity!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity!!,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        startLocationUpdates()

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
        if (mLocation == null) {
            startLocationUpdates()
        }
        if (mLocation != null) {
            var latitude : Double = mLocation.latitude
            var longtitude : Double = mLocation.longitude
        } else {

        }
    }

    private fun startLocationUpdates() {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        if (ActivityCompat.checkSelfPermission(activity!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity!!,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
    }
    override fun onConnectionSuspended(p0: Int) {
        Log.i(TAG, "Connection Suspended")
        mGoogleApiClient.connect()
    }

    override fun onConnectionFailed(connectResult: ConnectionResult) {
        Log.i(TAG, "Connection failed. Error : " + connectResult.getErrorCode())
    }

    override fun onStart() {
        super.onStart()
//        mapView.onStart()

        mGoogleApiClient.connect()
    }

    override fun onStop() {
        super.onStop()
//        mapView.onStop()

        if(mGoogleApiClient.isConnected) {
            mGoogleApiClient.disconnect()
        }
    }


    override fun onLocationChanged(p0: Location?) {}



    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallBack

    private val REQUEST_ACCESS_FINE_LOCATION = 1000

    // Google API Client 생성
    private lateinit var mGoogleApiClient : GoogleApiClient
    private lateinit var mLocation : Location
    private lateinit var locationManager : LocationManager
    private lateinit var mLocationRequest : LocationRequest

    private val TAG = javaClass.simpleName

    public var locationPermissionGranted:Boolean = false

    companion object {
        var mInstance: MapFragment? = null
        @Synchronized
        fun getInstance(): MapFragment {
            if (mInstance == null) {
                mInstance = MapFragment()
            }
            return mInstance!!
        }
//
//        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var  lastLocation : Location

    // 레이아웃 설정
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Fragment 내에서는 mapView로 지도 실행
        val view: View = inflater.inflate(R.layout.fragment_map, container, false)
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        val mapView : SupportMapFragment = (childFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment)!!
//        mapView.getMapAsync(this)

        mapView = view!!.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)

        view.btn_fragment_map_question.setOnClickListener {
            startActivityForResult<LocationListActivity>(999)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 999) {
            if(resultCode == 1) {

            }
            if(resultCode  == 2) {  // 어댑터에서 들어올 때 -> storeIdx 전달해서 서버 데이터 세팅 필요
                Log.d("@storeIdx통신@", "MapFragment로 돌아왔다.")
                var storeIdx : Int = data!!.getIntExtra("storeIdx", 0)
                Log.d("@storeIdx통신@", "storeIdx는? " + storeIdx)
                (activity as MainActivity).getStoreIdx(storeIdx)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        // 액티비티가 처음 생성될 때 실행되는 함수
        super.onActivityCreated(savedInstanceState)
        locationInit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGoogleApiClient = GoogleApiClient.Builder(activity!!)
                .addApi(LocationServices.API)
//                .addConnectionCallbacks(activity!!)
//                .addOnConnectFailedListener(activity!!)
                .build()

        mGoogleApiClient.connect()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(context)
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
            mMap.uiSettings.isCompassEnabled = true
            mMap.uiSettings.isZoomGesturesEnabled = true

        }



//        setUpMap()

        // Add a marker in Sydney and move the camera
//        val marker = LatLng(37.578346, 127.057015)
//        mMap.addMarker(MarkerOptions().position(marker).title("Marker"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(17f))

    }

//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        mapView.onSaveInstanceState(outState)
//    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()

        addLocationListener()

        // 권한 요청
        permissionCheck(cancel = {
            // 위치 정보가 필요한 이유 다이얼로그 표시
            showPermissionInfoDialog()
        }, ok = {
            // 현재 위치를 주기적으로 요청 (권한이 필요한 부분)
            addLocationListener()
        })
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()

        removeLocationListener()
    }

    private fun removeLocationListener() {
        // 현재 위치 요청을 삭제
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    // 위치 정보를 얻기 위한 각종 초기화
    private fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(activity!!)
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)

        locationCallback = MyLocationCallBack()

        locationRequest = LocationRequest()

        // GPS 우선
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        // 업데이트 인터벌
        // 위치 정보가 없을 때는 업데이트 안 함
        // 상황에 따라 짧아질 수 있음, 정확하지 않음
        // 다른 앱에서 짧은 인터벌로 위치 정보를 요청하면 짧아질 수 있음
        locationRequest.interval = 10000

        // 정확함. 이것보다 짧은 업데이트는 하지 않음
        locationRequest.fastestInterval = 5000
    }


    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback,
                null)

    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            location?.run {
                // 14 level로 확대하고 현재 위치로 카메라 이동
                val latLng = LatLng(latitude, longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
//                mMap.animateCamera(CameraUpdateFactory.zoomTo())

                Log.d("MapFragment", "위도 : $latitude, 경도 : $longitude")
            }
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
                ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
            }
        } else {
            // 권한을 수락했을 때 실행할 함수
            ok()
        }
    }

//    private fun setUpMap() {
//        if (ActivityCompat.checkSelfPermission(activity!!,
//                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity!!,
//                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
//            return
//        }
//
//        mMap.isMyLocationEnabled = true
//
//        fusedLocationProviderClient.lastLocation.addOnSuccessListener(activity!!) { location ->
//            if (location != null) {
//                lastLocation = location
//                val currentLatLng = LatLng (location.latitude, location.longitude)
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17f))
//            }
//        }
//    }

//    // 앱에 런타임 퍼미션 요청
//    private fun getDevicePermission() {
//        if (ContextCompat.checkSelfPermission(activity!!,
//                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            locationPermissionGranted = true
//        } else {
//            ActivityCompat.requestPermissions(activity!!,
//                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                    REQUEST_ACCESS_FINE_LOCATION)
//        }
//    }

    private fun showPermissionInfoDialog() {
        alert("현재 위치 정보를 얻으려면 위치 권한이 필요합니다", "권한이 필요한 이유") {
            yesButton {
                // 권한 요청
                ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_ACCESS_FINE_LOCATION)
            }
            noButton { }
        }.show()
    }

    // 콜백 메서드 오버라이드
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false

        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty()
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // 권한 혀용됨
                    addLocationListener()
                } else {
                    // 권한 거부
                    toast("권한 거부 됨")
                }
                return
            }
//
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    locationPermissionGranted = true
//                }
//            }
        }
//        updateLocationUI()
    }

//    fun updateLocationUI() {
//        if (mMap == null) {
//            return
//        }
//        try {
//            if (locationPermissionGranted) {
//                mMap.isMyLocationEnabled = true
//                mMap.uiSettings.isMyLocationButtonEnabled = true
//            } else {
//                mMap.isMyLocationEnabled = false
//                mMap.uiSettings.isMyLocationButtonEnabled = false
//                //lastKnownLocation = null
////                getLocationPermission()
//            }
//        } catch (e: SecurityException) {
//            Log.e("Exception: %s", e.message)
//        }
//    }


}