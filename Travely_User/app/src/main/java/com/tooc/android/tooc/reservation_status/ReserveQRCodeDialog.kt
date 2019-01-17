package com.tooc.android.tooc.reservation_status

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.tooc.android.tooc.R
import kotlinx.android.synthetic.main.dialog_qrcode_enlarge.*

class ReserveQRCodeDialog(var ctx : Context?, var qrCode : String) : Dialog(ctx){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_qrcode_enlarge)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        generateQRCode(qrCode)
        btn_qrcode_enlarge_close.setOnClickListener{
            dismiss()
        }
    }

    private fun generateQRCode(contents: String) {
        var qrCodeWriter = QRCodeWriter()
        var bitmap: Bitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 200, 200))

        iv_qrimage_enlarge.setImageBitmap(bitmap)
    }

    private fun toBitmap(matrix: BitMatrix): Bitmap {
        val height: Int = matrix.height
        val width: Int = matrix.width
        val bmp: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (matrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bmp
    }
}