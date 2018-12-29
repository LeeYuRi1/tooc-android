package com.hyeran.android.travely_user.map

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.PhotoRecylerViewAdapter
import com.hyeran.android.travely_user.adapter.ReviewRecyclerViewAdapter
import com.hyeran.android.travely_user.data.PhotoData
import com.hyeran.android.travely_user.data.ReviewData
import com.hyeran.android.travely_user.map.MapFragment.Companion.mInstance
import com.hyeran.android.travely_user.reserve.ReserveFragment
import kotlinx.android.synthetic.main.fragment_map_more.*
import kotlinx.android.synthetic.main.fragment_map_more_preview.*
import kotlinx.android.synthetic.main.fragment_map_more_preview.view.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton

class MapMorePreviewFragment : Fragment(), OnMapReadyCallback {
    //lateinit var mapView: MapView
    private lateinit var mMap2: GoogleMap
    private lateinit var mapView2: MapView

    private lateinit var fusedLocationProviderClient2: FusedLocationProviderClient
    private lateinit var locationRequest2: LocationRequest
    private lateinit var locationCallback2: MapMorePreviewFragment.MyLocationCallBack2

    private val REQUEST_ACCESS_FINE_LOCATION2 = 1000

    companion object {
        var mInstance2: MapMorePreviewFragment? = null
        @Synchronized
        fun getInstance(): MapMorePreviewFragment {
            if (mInstance2 == null) {
                mInstance2 = MapMorePreviewFragment()
            }
            return mInstance2!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map_more_preview, container, false)

        mapView2 = view.findViewById(R.id.mapView2)
        mapView2.onCreate(savedInstanceState)
        mapView2.getMapAsync(this)

        view.btn_fragment_map_more_preview.setOnClickListener {
            startActivity<MapMoreActivity>()
        }

        view.iv_reserve_map_more_preview.setOnClickListener {
            (activity as MainActivity).replaceFragment(ReserveFragment())
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locationInit2()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(context)
        mMap2 = googleMap

        // Add a marker in Sydney and move the camera
        val marker = LatLng(37.578346, 127.057015)
        mMap2.addMarker(MarkerOptions().position(marker).title("Marker"))
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

            val location = locationResult?.lastLocation

            location?.run {
                // 14 level로 확대하고 현재 위치로 카메라 이동
                val latLng = LatLng(latitude, longitude)
                mMap2.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
//                mMap2.animateCamera(CameraUpdateFactory.zoomTo())

                Log.d("MapMorePreviewFragment", "위도 : $latitude, 경도 : $longitude")
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

        removeLocationListener()
    }

    private fun removeLocationListener() {
        // 현재 위치 요청을 삭제
        fusedLocationProviderClient2.removeLocationUpdates(locationCallback2)
    }



}