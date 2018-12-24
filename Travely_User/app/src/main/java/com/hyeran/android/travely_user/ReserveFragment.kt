package com.hyeran.android.travely_user


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.WriterException
import android.R.attr.bitmap
import android.R.attr.y
import android.R.attr.x
import android.view.Display
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.net.Uri
import android.support.constraint.Constraints.TAG
import android.util.Log
import android.view.WindowManager
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileOutputStream
import android.R.attr.y
import android.R.attr.x
import android.graphics.Color
import com.google.zxing.BarcodeFormat


class ReserveFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_reserve, container, false)


        return v
    }


}
