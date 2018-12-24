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

class ReserveFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_reserve, container, false)
//
//        v.toggle.setOnClickListener {
//            v.rb_etc_reserve.toggle()
//            v.rb_carrier_reserve.toggle()
//
//            v.tv_selected_luggage_reserve.text = "노노"
//        }
//
//        v.rg_carrier_reserve.setOnCheckedChangeListener { group, checkedId ->
//            when (checkedId) {
//                R.id.rb_carrier_reserve -> {
//                    v.tv_selected_luggage_reserve.text = "캐리어"
//                }
//            }
//        }
//
//        v.rg_etc_reserve.setOnCheckedChangeListener { group, checkedId ->
//            when (checkedId) {
//                R.id.rb_etc_reserve -> {
//                    v.tv_selected_luggage_reserve.text = "기타 짐"
//                }
//            }
//        }
//        v.rb_carrier_reserve.setOnCheckedChangeListener { buttonView, isChecked ->
//
//            Toast.makeText(context, "캐리어 라디오 버튼", Toast.LENGTH_SHORT)
//        }
//        v.rb_carrier_reserve.setOnCheckedChangeListener {
//            Toast.makeText(context, "캐리어 라디오 버튼", Toast.LENGTH_SHORT)
//            if (v.rb_carrier_reserve.isChecked) {
//                Toast.makeText(context, "캐리어 라디오 버튼", Toast.LENGTH_SHORT)
//                v.rb_etc_reserve.isChecked = false
//            } else {
//                v.rb_etc_reserve.isChecked = true
//            }
//        }

//
//        if (v.rb_carrier_reserve.isChecked) {
//            Toast.makeText(context, "캐리어 라디오 버튼", Toast.LENGTH_SHORT)
////            v.rb_etc_reserve.isChecked = false
//        }
//        if (v.rb_etc_reserve.isChecked) {
//            Toast.makeText(context, "기타짐 라디오 버튼", Toast.LENGTH_SHORT)
////            v.rb_carrier_reserve.isChecked = false
//        }

        v.btn_reserve_reserve.setOnClickListener {
            ReserveCompleteDialog(context).show()
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
