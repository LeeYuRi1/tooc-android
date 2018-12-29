package com.hyeran.android.travely_user.reserve

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hyeran.android.travely_user.R
import kotlinx.android.synthetic.main.dialog_reserve_time_setting.*
import kotlinx.android.synthetic.main.dialog_reserve_time_setting.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ReserveTimeSettintDialog : DialogFragment() {

    var dates: Array<String>? = null
    var rightNow = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val v = inflater.inflate(R.layout.dialog_reserve_time_setting, container, false)
        dates = datesFromCalender()

        var calen = Calendar.getInstance()
        val dateformat = SimpleDateFormat("MMM dd일 (EE)")
        //val textDate = dateformat.format(calen.time)

        v.tv_store_date.text = dateformat.format(calen.time)


        //store에서 피커뷰 사용~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        v.pv_store_date.minValue = 0
        v.pv_store_date.maxValue = dates!!.size - 1
        v.pv_store_date.displayedValues = dates

        v.pv_store_hour.minValue = 0
        v.pv_store_hour.maxValue = 23
        v.pv_store_hour.value = rightNow.get(Calendar.HOUR_OF_DAY)
        v.pv_store_minute.minValue = 0
        v.pv_store_minute.maxValue = 59
        v.pv_store_minute.value = rightNow.get(Calendar.MINUTE)

        v.pv_store_date.wrapSelectorWheel = false


        v.pv_store_date.setOnValueChangedListener { numberPicker, i, j ->
            calen.add(Calendar.DATE,v.pv_store_date.value)
            v.tv_store_date.text = dateformat.format(calen.time)
            calen.add(Calendar.DATE,-v.pv_store_date.value)

        }
        v.pv_store_hour.setOnValueChangedListener { numberPicker, i, j ->
            v.tv_store_hour.text = v.pv_store_hour.value.toString()
        }
        v.pv_store_minute.setOnValueChangedListener { numberPicker, i, j ->
            v.tv_store_minute.text = v.pv_store_minute.value.toString()
        }


        //take피커뷰 사용~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        v.pv_take_date.minValue = 0
        v.pv_take_date.maxValue = dates!!.size - 1
        //   v.custom_picker_date_full.setFormatter(NumberPicker.Formatter { value -> dates!![value] })
        v.pv_take_date.displayedValues = dates


        v.pv_take_hour.minValue = 0
        v.pv_take_hour.maxValue = 23
        v.pv_take_hour.value = rightNow.get(Calendar.HOUR_OF_DAY)
        v.pv_take_minute.minValue = 0
        v.pv_take_minute.maxValue = 59
        v.pv_take_minute.value = rightNow.get(Calendar.MINUTE)

        v.pv_take_date.wrapSelectorWheel = false

        v.pv_take_date.setOnValueChangedListener { numberPicker, i, j ->
            calen.add(Calendar.DATE,v.pv_take_date.value)
            v.tv_take_date.text = dateformat.format(calen.time)
            calen.add(Calendar.DATE,-v.pv_take_date.value)
        }
        v.pv_take_hour.setOnValueChangedListener { numberPicker, i, j ->
            v.tv_take_hour.text = v.pv_take_hour.value.toString()
        }
        v.pv_take_minute.setOnValueChangedListener { numberPicker, i, j ->
            v.tv_take_minute.text = v.pv_take_minute.value.toString()
        }

        v.btn_store_time.setBackgroundColor(Color.parseColor("#4C64FD"))


        //버튼클릭시~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        var alertFlag = 0;

        v.btn_store_time.setOnClickListener {
            if (alertFlag == 1) {
                vs_custom_date_picker.showNext()
                alertFlag = 0;
            }
            v.btn_store_time.setBackgroundColor(Color.parseColor("#4C64FD"))
            v.btn_take_time.setBackgroundColor(Color.parseColor("#ffffff"))
        }

        v.btn_take_time.setOnClickListener {
            if (alertFlag == 0) {
                vs_custom_date_picker.showNext()
                alertFlag = 1;
            }
            v.btn_take_time.setBackgroundColor(Color.parseColor("#4C64FD"))
            v.btn_store_time.setBackgroundColor(Color.parseColor("#ffffff"))
        }

        return v
    }

    private fun datesFromCalender(): Array<String> {
        val c1 = Calendar.getInstance()

        val dates = ArrayList<String>()
        //val dateFormat = SimpleDateFormat("EEE, dd MMM")
        val dateFormat = SimpleDateFormat("MMM dd일 EE")

        dates.add(dateFormat.format(c1.time))

        for (i in 0..59) {
            c1.add(Calendar.DATE, 1)
            dates.add(dateFormat.format(c1.time))
        }

        return dates.toTypedArray()
    }

}