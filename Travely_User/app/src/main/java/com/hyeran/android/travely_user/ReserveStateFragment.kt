package com.hyeran.android.travely_user


import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.fragment_reserve_state.*
import android.R.attr.bitmap
import android.R.attr.data
import android.content.Context
import android.content.Intent
import android.graphics.Picture
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.EditText
import com.bumptech.glide.Glide
import com.google.zxing.LuminanceSource
import kotlinx.android.synthetic.main.fragment_reserve_state.view.*
import org.jetbrains.anko.support.v4.startActivity


class ReserveStateFragment : Fragment() {

    lateinit var luggagePictureAdapter: LuggagePictureAdapter

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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        setRecyclerView()
//    }

    fun setRecyclerView() {
        var dataList: ArrayList<LuggagePictureData> = ArrayList()
//        dataList.add(LuggagePictureData(Drawable.))
        dataList.add(LuggagePictureData("tesetest"))
        dataList.add(LuggagePictureData("tesetest"))


        luggagePictureAdapter = LuggagePictureAdapter(activity!!, dataList)
        rv_luggage_picture.adapter = luggagePictureAdapter
        rv_luggage_picture.layoutManager = LinearLayoutManager(context)
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
