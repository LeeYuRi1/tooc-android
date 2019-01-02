package com.hyeran.android.travely_user.reserve

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hyeran.android.travely_user.MainActivity

import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.R.id.*
import kotlinx.android.synthetic.main.dialog_reserve_time_setting.*
import kotlinx.android.synthetic.main.dialog_reserve_time_setting.view.*
import kotlinx.android.synthetic.main.fragment_faq.view.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ReserveTimeSettintDialog(val ctx: Context?, val reiceveArray: ArrayList<Any>) : Dialog(ctx) {

    //TODO : 휴무일 넣어야함
    var offday: String? = "일"

    var dates: Array<String>? = null
    var hours: Array<String>? = null
    var minutes: Array<String>? = null
    var rightNow = Calendar.getInstance()
    val weekFormat = SimpleDateFormat("E")

    var tcalen = Calendar.getInstance()
    var scalen = Calendar.getInstance()

    val dateformat = SimpleDateFormat("MMM dd일 (EE)")
    val allFormat = SimpleDateFormat("yy MMM dd일 kk:mm")

    var storeProhibit: String? = null
    var takeProhibit: String? = null

    var allstore: String? = null
    var alltake: String? = null


    var smmddee: String? = null
    var tmmddee: String? = null
    var shh: String? = null
    var smm: String? = null
    var thh: String? = null
    var tmm: String? = null

    var sText: String = reiceveArray[0].toString()
    var tText: String = reiceveArray[3].toString()
    var svalue: Int = reiceveArray[6] as Int
    var tvalue: Int = reiceveArray[7] as Int
    var snumhh: Int = reiceveArray[1] as Int
    var snummm: Int = reiceveArray[2] as Int
    var tnumhh: Int = reiceveArray[4] as Int
    var tnummm: Int = reiceveArray[5] as Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_reserve_time_setting)

        // Toast.makeText(context,svalue.toString()+"   "+tvalue,Toast.LENGTH_LONG).show()

        dates = datesFromCalender()
        hours = hourFromCalender()
        minutes = minuteFromCalender()

        tv_store_date.text = dateformat.format(scalen.time)
        tv_store_date.text = sText
        tv_take_date.text = dateformat.format(tcalen.time)
        tv_take_date.text = tText
        storeProhibit = weekFormat.format(scalen.time)
        takeProhibit = weekFormat.format(tcalen.time)
        allstore = allFormat.format(scalen.time)
        alltake = allFormat.format(tcalen.time)

        //store에서 피커뷰 사용~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        pv_store_date.minValue = 0
        pv_store_date.maxValue = dates!!.size - 1
        pv_store_date.displayedValues = dates
        pv_store_date.value = svalue
        //tv_store_date.text = dateformat.format()

        pv_store_hour.minValue = 0
        pv_store_hour.maxValue = hours!!.size - 1
        pv_store_hour.displayedValues = hours
        pv_store_hour.value = snumhh
        if (pv_store_hour.value < 10) {
            shh = "0" + pv_store_hour.value.toString()
        } else shh = pv_store_hour.value.toString()
        tv_store_hour.text = shh

        pv_store_minute.minValue = 0
        pv_store_minute.maxValue = 59
        pv_store_minute.displayedValues = minutes
        pv_store_minute.value = snummm
        if (pv_store_minute.value < 10) {
            smm = "0" + pv_store_minute.value.toString()
        } else smm = pv_store_minute.value.toString()
        tv_store_minute.text = smm

        pv_store_date.wrapSelectorWheel = false

        smmddee = sText

        pv_store_date.setOnValueChangedListener { numberPicker, i, j ->
            scalen.add(Calendar.DATE, pv_store_date.value)
            svalue = pv_store_date.value
            tv_store_date.text = dateformat.format(scalen.time)
            smmddee = dateformat.format(scalen.time)
            storeProhibit = weekFormat.format(scalen.time).toString()
            scalen.add(Calendar.DATE, -pv_store_date.value)
        }
        pv_store_hour.setOnValueChangedListener { numberPicker, i, j ->
            snumhh = pv_store_hour.value
            if (pv_store_hour.value < 10) {
                shh = "0" + pv_store_hour.value.toString()
            } else shh = pv_store_hour.value.toString()
            tv_store_hour.text = shh
        }
        pv_store_minute.setOnValueChangedListener { numberPicker, i, j ->
            snummm = pv_store_minute.value
            if (pv_store_minute.value < 10) {
                smm = "0" + pv_store_minute.value.toString()
            } else smm = pv_store_minute.value.toString()
            tv_store_minute.text = smm
        }

        //take피커뷰 사용~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        pv_take_date.minValue = 0
        pv_take_date.maxValue = dates!!.size - 1
        pv_take_date.displayedValues = dates
        pv_take_date.value = tvalue

        pv_take_hour.minValue = 0
        pv_take_hour.maxValue = 23
        //pv_take_hour.value = rightNow.get(Calendar.HOUR_OF_DAY) + 4
        pv_take_hour.displayedValues = hours
        pv_take_hour.value = tnumhh
        if (pv_take_hour.value < 10) {
            thh = "0" + pv_take_hour.value.toString()
        } else thh = pv_take_hour.value.toString()
        tv_take_hour.text = thh

        pv_take_minute.minValue = 0
        pv_take_minute.maxValue = 59
        // pv_take_minute.value = rightNow.get(Calendar.MINUTE)
        pv_take_minute.displayedValues = minutes
        pv_take_minute.value = tnummm
        if (pv_take_minute.value < 10) {
            tmm = "0" + pv_take_minute.value.toString()
        } else tmm = pv_take_minute.value.toString()
        tv_take_minute.text = tmm

        tmmddee = tText

        pv_take_date.wrapSelectorWheel = false

        pv_take_date.setOnValueChangedListener { numberPicker, i, j ->
            tcalen.add(Calendar.DATE, pv_take_date.value)
            tvalue = pv_take_date.value
            takeProhibit = weekFormat.format(tcalen.time).toString()
            tmmddee = dateformat.format(tcalen.time)
            tv_take_date.text = dateformat.format(tcalen.time)
            tcalen.add(Calendar.DATE, -pv_take_date.value)
        }
        pv_take_hour.setOnValueChangedListener { numberPicker, i, j ->
            tnumhh = pv_take_hour.value
            if (pv_take_hour.value < 10) {
                thh = "0" + pv_take_hour.value.toString()
            } else thh = pv_take_hour.value.toString()
            tv_take_hour.text = thh
        }
        pv_take_minute.setOnValueChangedListener { numberPicker, i, j ->
            tnummm = pv_take_minute.value
            if (pv_take_minute.value < 10) {
                tmm = "0" + pv_take_minute.value.toString()
            } else tmm = pv_take_minute.value.toString()
            tv_take_minute.text = tmm
        }

        btn_store_time.setBackgroundColor(Color.parseColor("#4C64FD"))


        //버튼클릭시~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        setOnClickListner()
    }


    private fun datesFromCalender(): Array<String> {
        val c1 = Calendar.getInstance()
        val dates = ArrayList<String>()
        val dateFormat = SimpleDateFormat("MMM dd일 (EE)")

        if (weekFormat.format(c1.time) == offday) {
            dates.add(dateFormat.format(c1.time) + " (휴무)")
        } else {
            dates.add(dateFormat.format(c1.time))
        }
        for (i in 0..59) {
            c1.add(Calendar.DATE, 1)
            if (weekFormat.format(c1.time) == offday) {
                dates.add(dateFormat.format(c1.time) + " (휴무)")
            } else {
                dates.add(dateFormat.format(c1.time))
            }
        }
        return dates.toTypedArray()
    }

    private fun hourFromCalender(): Array<String> {
        val dates = ArrayList<String>()
        for (i in 0..9) dates.add("0" + i)
        for (i in 10..23) dates.add(i.toString())
        return dates.toTypedArray()
    }

    private fun minuteFromCalender(): Array<String> {
        val dates = ArrayList<String>()
        for (i in 0..9) dates.add("0" + i)
        for (i in 10..59) dates.add(i.toString())
        return dates.toTypedArray()
    }

    fun setOnClickListner() {
        var alertFlag = 0;

        btn_store_time.setOnClickListener {
            if (alertFlag == 1 && takeProhibit != offday) {
                vs_custom_date_picker.showNext()
                alertFlag = 0;
                btn_store_time.setBackgroundColor(Color.parseColor("#4C64FD"))
                btn_take_time.setBackgroundColor(Color.parseColor("#ffffff"))
            } else {
                var ttoast: Toast = Toast.makeText(context, "    상가 영업시간이 아닙니다.\n예약 시간을 다시 설정해주세요.", Toast.LENGTH_LONG)
                ttoast.setGravity(Gravity.CENTER, 0, 0)
                ttoast.show()
            }

        }

        btn_take_time.setOnClickListener {
            if (alertFlag == 0 && storeProhibit != offday) {
                vs_custom_date_picker.showNext()
                alertFlag = 1;
                btn_take_time.setBackgroundColor(Color.parseColor("#4C64FD"))
                btn_store_time.setBackgroundColor(Color.parseColor("#ffffff"))
            } else {
                var ttoast: Toast = Toast.makeText(context, "    상가 영업시간이 아닙니다.\n예약 시간을 다시 설정해주세요.", Toast.LENGTH_LONG)
                ttoast.setGravity(Gravity.CENTER, 0, 0)
                ttoast.show()
            }
        }

        btn_time_confirm.setOnClickListener {

            if (takeProhibit == offday) {
                var ttoast: Toast = Toast.makeText(context, "    상가 영업시간이 아닙니다.\n예약 시간을 다시 설정해주세요.", Toast.LENGTH_LONG)
                ttoast.setGravity(Gravity.CENTER, 0, 0)
                ttoast.show()
            } else {
                if ((0 > pv_take_date.value - pv_store_date.value)) {
                    Toast.makeText(context, "시간 설정이 잘못되었습니다.", Toast.LENGTH_LONG).show()
                } else if ((0 == pv_take_date.value - pv_store_date.value)) {
                    if (0 > pv_take_hour.value - pv_store_hour.value) {
                        Toast.makeText(context, "시간 설정이 잘못되었습니다.", Toast.LENGTH_LONG).show()
                    } else if (0 == pv_take_hour.value - pv_store_hour.value) {
                        if (0 > pv_take_minute.value - pv_store_minute.value) {
                            Toast.makeText(context, "시간 설정이 잘못되었습니다.", Toast.LENGTH_LONG).show()
                        } else {
                            (ctx as MainActivity).getTimeSettingDialog(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm, svalue, tvalue)
                            dismiss()
                        }
                    } else {
                        (ctx as MainActivity).getTimeSettingDialog(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm, svalue, tvalue)
                        dismiss()
                    }
                } else {
                    (ctx as MainActivity).getTimeSettingDialog(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm, svalue, tvalue)
                    dismiss()
                }
            }
//            if (takeProhibit == offday) {
//                var ttoast: Toast = Toast.makeText(context, "    상가 영업시간이 아닙니다.\n예약 시간을 다시 설정해주세요.", Toast.LENGTH_LONG)
//                ttoast.setGravity(Gravity.CENTER, 0, 0)
//                ttoast.show()
//            } else {
//                if (pv_store_date.value >= pv_take_date.value) {
//                    if (pv_store_hour.value >= pv_take_hour.value) {
//                        if (pv_store_minute.value >= pv_take_minute.value) {
//                            Toast.makeText(context, "시간 설정이 잘못되었습니다.", Toast.LENGTH_LONG).show()
//                        }
//                    }
//                } else {
//                    (ctx as MainActivity).getTimeSettingDialog(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm, svalue, tvalue)
//                    dismiss()
//                }
//            }
        }

    }

}