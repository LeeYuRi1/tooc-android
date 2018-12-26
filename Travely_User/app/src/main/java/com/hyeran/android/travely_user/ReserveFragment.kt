package com.hyeran.android.travely_user


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_reserve.*
import kotlinx.android.synthetic.main.fragment_reserve.view.*
import org.jetbrains.anko.support.v4.toast

class ReserveFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_reserve, container, false)



        v.btn_reserve_reserve.setOnClickListener {
            if(v.cb_confirm_reserve.isChecked) {
                ReserveCompleteDialog(context).show()
            }
            else
            {
                toast("예약 동의를 체크해주세요.")
            }
        }
        return v
    }

}