package com.hyeran.android.travely_manager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.integration.android.IntentIntegrator



class QRCodeReaderFragment : Fragment() {

    companion object {
        var mInstance: QRCodeReaderFragment? = null

        @Synchronized
        fun getInstance(): QRCodeReaderFragment {
            if (mInstance == null) {
                mInstance = QRCodeReaderFragment()
            }
            return mInstance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_one, container, false)

        IntentIntegrator(activity).initiateScan()


        return v
    }



}
