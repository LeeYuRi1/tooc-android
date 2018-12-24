package com.hyeran.android.travely_user

import android.graphics.Camera
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {
    lateinit var rootView : View
    lateinit var mapView : MapView

    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MapActivity.MyLocationCallBack

    private val REQUEST_ACCESS_FINE_LOCATION = 1000


    override fun onMapReady(p0: GoogleMap) {
        val mMap = p0

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    companion object {
        var mInstance: MapFragment? = null

        @Synchronized
        fun getInstance(): MapFragment {
            if (mInstance == null) {
                mInstance = MapFragment()
            }
            return mInstance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Fragment 내에서는 mapView로 지도 실행
        val rootView : View = inflater.inflate(R.layout.fragment_map, container, false)
        val mapView : MapView = rootView.findViewById(R.id.mapView) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return rootView
    }

//    override fun onResume() {
//        mapView.onResume()
//        super.onResume()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mapView.onDestroy()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        mapView.onLowMemory()
//    }

}
