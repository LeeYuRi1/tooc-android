package com.hyeran.android.travely_user.reserve_state


import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
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
import com.hyeran.android.travely_user.model.reservation.ReservationReserveCodeData
import com.hyeran.android.travely_user.model.reservation.bagDtosData
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.fragment_reserve_state.view.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat


class ReserveStateFragment : Fragment(), OnMapReadyCallback {

    lateinit var networkService: NetworkService
    private lateinit var mMap3: GoogleMap
    private lateinit var mapView3: MapView
    private lateinit var mGoogleApiClient3: GoogleApiClient

    var latitude3: Double = 0.0
    var longitude3: Double = 0.0
  //  var latitude: Double = 0.0
  //  var longitude: Double = 0.0

    override fun onMapReady(googleMap: GoogleMap?) {
        MapsInitializer.initialize(context)
        mMap3 = googleMap!!

//        val marker = LatLng(latitude, longitude)
//        mMap3.addMarker(MarkerOptions().position(marker).title("동대문엽기떡볶이 홍대점").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_color_pin)))
//        mMap3.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 17f))
    }


    fun setGoogleMap(shopName: String, latitude:Double,longitude:Double){
        var marker = LatLng(latitude,longitude)
        Log.d("TAGGGG","lat = "+latitude+"  lon = "+longitude)
        mMap3.addMarker(MarkerOptions().position(marker).title(shopName).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_color_pin)))
        mMap3.moveCamera(CameraUpdateFactory.newLatLngZoom(marker,17f))
    }


    lateinit var luggagePictureAdapter: LuggagePictureAdapter

    var stateType: String? = null
    var reserveCode: String? = null
    var startTime: Long? = null
    var endTime: Long? = null
    var bagDtos = ArrayList<bagDtosData>()


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

    var password: String? = null

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
        var v = inflater.inflate(R.layout.fragment_reserve_state, container, false)

        networkService = ApplicationController.instance.networkService
        getReservationReserveResponse(v)

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

                var mLastKnownLocation: Location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient3)
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

    fun getReservationReserveResponse(v: View) {
        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        val getReservationReserveResponse = networkService.getReservationReserveResponse(jwt)

        getReservationReserveResponse!!.enqueue(object : retrofit2.Callback<ReservationReserveCodeData> {
            override fun onFailure(call: Call<ReservationReserveCodeData>, t: Throwable) {
                toast("fail" + t.message)
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
                            generateQRCode(v, response.body()!!.reserveCode)
                            toast("stateType = " + stateType)
                            if (stateType == "RESERVED") {
                                iv_circle_settlement_reservestate.setImageResource(R.drawable.ic_circle_empty)
                                iv_circle_storage_reservestate.setImageResource(R.drawable.ic_circle_empty)
                                iv_circle_collect_reservestate.setImageResource(R.drawable.ic_circle_empty)
                                iv_payment_complete_reservestate.setImageResource(R.drawable.box_pay_no)
                            } else if (stateType == "PAYED") {
                                iv_circle_settlement_reservestate.setImageResource(R.drawable.ic_circle_fill)
                                iv_circle_storage_reservestate.setImageResource(R.drawable.ic_circle_empty)
                                iv_circle_collect_reservestate.setImageResource(R.drawable.ic_circle_empty)
                                iv_payment_complete_reservestate.setImageResource(R.drawable.box_pay_yes)

                            } else if (stateType == "ARCHIVE") {
                                iv_circle_settlement_reservestate.setImageResource(R.drawable.ic_circle_fill)
                                iv_circle_storage_reservestate.setImageResource(R.drawable.ic_circle_fill)
                                iv_circle_collect_reservestate.setImageResource(R.drawable.ic_circle_empty)
                                iv_payment_complete_reservestate.setImageResource(R.drawable.box_pay_yes)
                            } else if (stateType == "PICKUP") {
                                iv_circle_settlement_reservestate.setImageResource(R.drawable.ic_circle_fill)
                                iv_circle_storage_reservestate.setImageResource(R.drawable.ic_circle_fill)
                                iv_circle_collect_reservestate.setImageResource(R.drawable.ic_circle_fill)
                                iv_payment_complete_reservestate.setImageResource(R.drawable.box_pay_yes)
                            } else if (stateType == "CANCEL") {
                                ReserveCancelDialog(ctx).show()
                            }
                            //DATE
                            var dateTextFormat = SimpleDateFormat("yy년 mm월 dd일 EE요일")
                            tv_put_year_reservestate.text = dateTextFormat.format(startTime)
                            tv_find_year_reservestate.text = dateTextFormat.format(endTime)
                            //시간
                            var timeTextFormat = SimpleDateFormat("a HH시 mm분")
                            tv_put_ampm_reservestate.text = timeTextFormat.format(startTime)
                            tv_find_ampm_reservestate.text = timeTextFormat.format(endTime)
                            //QR
                            tv_qr_reserveCode.text = reserveCode.toString()
                            //위도경도
//                            setGoogleMap(response.body()!!.store.storeName,latitude,longitude)

                            toast("bagDtos Size : "+response.body()!!.bagDtos.size)
                            for(i in 0..response.body()!!.bagDtos.size){
                                response.body()!!.bagDtos[0].bagType
                            }

                            Log.d("TAGG", "bagDtos : " + bagDtos.toString())
                        }
                        500 -> {
                            toast("500 error")
                        }
                        else -> toast("error")
                    }
                }
            }
        })

    }

}
