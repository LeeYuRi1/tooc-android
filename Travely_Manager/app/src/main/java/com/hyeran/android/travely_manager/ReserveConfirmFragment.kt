package com.hyeran.android.travely_manager

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_reserve_confirm.*
import kotlinx.android.synthetic.main.fragment_reserve_confirm.view.*

class ReserveConfirmFragment : Fragment() {

    companion object {
        var mInstance: ReserveConfirmFragment? = null

        @Synchronized
        fun getInstance(): ReserveConfirmFragment {
            if (mInstance == null) {
                mInstance = ReserveConfirmFragment()
            }

            return mInstance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_reserve_confirm, container, false)


        v.iv_qrcode_scan_reserve_confirm.setOnClickListener {
            IntentIntegrator(activity).initiateScan()
        }
        v.btn_reserve_number_input.setOnClickListener{
            ReserveNumberDialog(context).show()
            //(activity as MainActivity).qrCode("123")

        }

        return v
    }
}
