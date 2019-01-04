package com.hyeran.android.travely_user.reserve_state


import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
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
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.LuggagePictureAdapter
import com.hyeran.android.travely_user.data.LuggagePictureData
import com.hyeran.android.travely_user.dialog.ReserveCancelDialog
import com.hyeran.android.travely_user.model.reservation.ReservationReserveCodeData
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.fragment_reserve_state.view.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class ReserveStateFragment : Fragment() {

    lateinit var networkService: NetworkService

    lateinit var luggagePictureAdapter: LuggagePictureAdapter


    var stateType : String? = null
    var reserveCode : String? = null
    var startTIme : Long? = null
    var endTime : Long? = null
    var bagType = ArrayList<String>()
    var bagCount = ArrayList<Int>()

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
        return v
    }

    override fun onStart() {
        super.onStart()
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

    private fun init(){
        networkService = ApplicationController.instance.networkService
    }
    fun getReservationReserveResponse(){
        var jwt : String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        val getReservationReserveResponse = networkService.getReservationReserveResponse(jwt)
        getReservationReserveResponse.enqueue(object : retrofit2.Callback<ReservationReserveCodeData>{
            override fun onFailure(call: Call<ReservationReserveCodeData>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<ReservationReserveCodeData>, response: Response<ReservationReserveCodeData>) {
                response?.let {
                    when(it?.code()){
                        200->{
                            stateType = response.body()!!.stateType.toString()
                            reserveCode = response.body()!!.reserveCode.toString()
//                            var startTIme : Long? = null
//                            var endTime : Long? = null
//                            var bagType = ArrayList<String>()
//                            var bagCount = ArrayList<Int>()

                        }
                        500->{
                            toast("500 error")
                        }
                        else -> toast("toast")
                    }
                }
            }
        })

    }
}
