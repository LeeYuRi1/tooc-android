package com.hyeran.android.travely_user.reserve_state


import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.fragment_reserve_state.*
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.LuggagePictureAdapter
import com.hyeran.android.travely_user.data.LuggagePictureData
import com.hyeran.android.travely_user.dialog.MapChoiceDialog
import com.hyeran.android.travely_user.dialog.ReserveCancelDialog
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_reserve_state.view.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class ReserveStateFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap3 : GoogleMap
    private lateinit var mapView3 : MapView
    private lateinit var mGoogleApiClient3: GoogleApiClient

    var latitude3 : Double = 0.0
    var longitude3 : Double = 0.0

    override fun onMapReady(googleMap: GoogleMap?) {
        MapsInitializer.initialize(context)
        mMap3 = googleMap!!

        val marker = LatLng(37.578346, 127.057015)
        mMap3.addMarker(MarkerOptions().position(marker).title("동대문엽기떡볶이 홍대점").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_color_pin)))
        mMap3.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 17f))
    }

    lateinit var luggagePictureAdapter: LuggagePictureAdapter

    companion object {
        private var instance: ReserveStateFragment? = null

        fun getInstance(password: String): ReserveStateFragment {
            if (instance == null) {
                instance = ReserveStateFragment().apply {
                    arguments = Bundle().apply { putString("password", password) }
                }
            }
            return instance!!
        }
    }

    var password : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGoogleApiClient3 = GoogleApiClient.Builder(activity!!)
                .addApi(LocationServices.API)
//                .addConnectionCallbacks(activity!!)
//                .addOnConnectFailedListener(activity!!)
                .build()
        mGoogleApiClient3.connect()

        arguments?.let { password = it.getString("password") }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_reserve_state, container, false)

        var testt: String = "TEst"
        generateQRCode(v, testt)

        v.btn_reservecancel_to_dialog.setOnClickListener {
            ReserveCancelDialog(context).show()
        }
        v.iv_qrimage_reservestate.setOnClickListener {
            startActivity<ReserveQRCodeActivity>()
        }

        mapView3 = v.findViewById(R.id.mv_store_map_reservestate)
        mapView3.onCreate(savedInstanceState)
        mapView3.getMapAsync(this)

        v.iv_store_guide_reservestate.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(activity!!,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(activity!!,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                var mLastKnownLocation : Location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient3)
                latitude3 = mLastKnownLocation.latitude
                longitude3 = mLastKnownLocation.longitude

//                Log.d("ReserveStateFragment", "위도 : $latitude3, 경도 : $longitude3")
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

        setRecyclerView()
    }


    fun setRecyclerView() {
        var dataList: ArrayList<LuggagePictureData> = ArrayList()
//        dataList.add(LuggagePictureData(Drawable.))
        dataList.add(LuggagePictureData("tesetest"))
        dataList.add(LuggagePictureData("tesetest"))

        luggagePictureAdapter = LuggagePictureAdapter(activity!!, dataList)
        rv_luggage_picture.adapter = luggagePictureAdapter
        var mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_luggage_picture.layoutManager = mLayoutManager
    }

    fun generateQRCode(view: View, contents: String) {
        var qrCodeWriter = QRCodeWriter()
        var bitmap: Bitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 200, 200))

        view.iv_qrimage_reservestate.setImageBitmap(bitmap)
        Log.v("tag", bitmap.toString())
    }

    fun toBitmap(matrix: BitMatrix): Bitmap {
        val height: Int = matrix.height
        val width: Int = matrix.width
        val bmp: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0..width - 1) {
            for (y in 0..height - 1) {
                bmp.setPixel(x, y, if (matrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bmp
    }

}
