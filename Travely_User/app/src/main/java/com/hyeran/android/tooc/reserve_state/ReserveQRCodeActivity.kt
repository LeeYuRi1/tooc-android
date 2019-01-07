package com.hyeran.android.tooc.reserve_state

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.R.id.btn_qrcode_enlarge_close
import com.hyeran.android.tooc.R.id.iv_qrimage_enlarge
import kotlinx.android.synthetic.main.dialog_qrcode_enlarge.*
import org.jetbrains.anko.toast

class ReserveQRCodeActivity(var ctx :Context?,var qrCode:String) :Dialog(ctx){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_qrcode_enlarge)

        generateQRCode(qrCode)

        btn_qrcode_enlarge_close.setOnClickListener{
            dismiss()
        }
    }

    fun generateQRCode( contents: String) {
        var qrCodeWriter = QRCodeWriter()
        var bitmap: Bitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 200, 200))

        iv_qrimage_enlarge.setImageBitmap(bitmap)
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