package com.hyeran.android.travely_user.reserve

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.*
import android.widget.Toast
import com.hyeran.android.travely_user.MainActivity

import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.R.color.mainColor
import com.hyeran.android.travely_user.R.id.*
import com.hyeran.android.travely_user.map.MapMorePreviewFragment
import kotlinx.android.synthetic.main.dialog_reserve_time_setting.*
import kotlinx.android.synthetic.main.dialog_reserve_time_setting.view.*
import kotlinx.android.synthetic.main.fragment_faq.view.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ReserveTimeSettintDialog(val ctx: Context?, val reiceveArray: ArrayList<Any>, val offday: ArrayList<String>) : Dialog(ctx) {

    var offdaySize = offday.size - 1

    var dates: Array<String>? = null
    var hours: Array<String>? = null
    var minutes: Array<String>? = null
    var rightNow = Calendar.getInstance()
    val weekFormat = SimpleDateFormat("E")

    var tcalen = Calendar.getInstance()
    var scalen = Calendar.getInstance()

    val dateformat = SimpleDateFormat("MMM dd일 (EE)")
    val allFormat = SimpleDateFormat("yy MMM dd일 kk:mm")
    var hourFormat = SimpleDateFormat("kk")

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
    var storeIdx:Int = reiceveArray[10] as Int

    var openTime: Int = hourFormat.format(reiceveArray[8] as Long).toInt()
    var closeTime: Int = hourFormat.format(reiceveArray[9] as Long).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_reserve_time_setting)

        iv_store_time_image.setColorFilter(Color.parseColor("#ffffff"))
        var offDayText : String = ""
        if(offdaySize>=0){
            for(i in 0..offdaySize){
                offDayText += offday[i]+"요일"
                if(i != offdaySize) {
                    offDayText +=", "
                }
            }
            tv_week_input.text = offDayText
        }else{
            tv_week_front.visibility = View.INVISIBLE
            tv_week_end.visibility = View.INVISIBLE
            tv_week_input.visibility = View.INVISIBLE
        }

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
        pv_store_hour.value = snumhh - openTime
        if (pv_store_hour.value + openTime < 10) {
            shh = "0" + (pv_store_hour.value + openTime).toString()
        } else shh = (pv_store_hour.value + openTime).toString()
        tv_store_hour.text = shh
        snumhh = pv_store_hour.value + openTime

        pv_store_minute.minValue = 0
        pv_store_minute.maxValue = 59
        pv_store_minute.displayedValues = minutes
        pv_store_minute.value = snummm
        if (pv_store_minute.value < 10) {
            smm = "0" + pv_store_minute.value.toString()
        } else smm = pv_store_minute.value.toString()
        tv_store_minute.text = smm

        pv_store_date.wrapSelectorWheel = false
        pv_store_hour.wrapSelectorWheel = false

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
            snumhh = pv_store_hour.value + openTime
            if (pv_store_hour.value + openTime < 10) {
                shh = "0" + (pv_store_hour.value + openTime).toString()
            } else shh = (pv_store_hour.value + openTime).toString()
            tv_store_hour.text = shh
        }
        pv_store_minute.setOnValueChangedListener { numberPicker, i, j ->
            snummm = pv_store_minute.value
            if (pv_store_minute.value < 10) {
                smm = "0" + pv_store_minute.value.toString()
            } else smm = pv_store_minute.value.toString()
            tv_store_minute.text = smm
        }

        //take피커뷰 사용~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        pv_take_date.minValue = 0
        pv_take_date.maxValue = dates!!.size - 1
        pv_take_date.displayedValues = dates
        pv_take_date.value = tvalue

        pv_take_hour.minValue = 0
        pv_take_hour.maxValue = hours!!.size - 1
        pv_take_hour.displayedValues = hours
        pv_take_hour.value = tnumhh - openTime
        if (pv_take_hour.value + openTime < 10) {
            thh = "0" + (pv_take_hour.value + openTime).toString()
        } else thh = (pv_take_hour.value + openTime).toString()
        tv_take_hour.text = thh
        tnumhh = pv_take_hour.value + openTime


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
        pv_take_hour.wrapSelectorWheel = false


        pv_take_date.setOnValueChangedListener { numberPicker, i, j ->
            tcalen.add(Calendar.DATE, pv_take_date.value)
            tvalue = pv_take_date.value
            takeProhibit = weekFormat.format(tcalen.time).toString()
            tmmddee = dateformat.format(tcalen.time)
            tv_take_date.text = dateformat.format(tcalen.time)
            tcalen.add(Calendar.DATE, -pv_take_date.value)
        }
        pv_take_hour.setOnValueChangedListener { numberPicker, i, j ->
            tnumhh = pv_take_hour.value + openTime
            if (pv_take_hour.value + openTime < 10) {
                thh = "0" + (pv_take_hour.value + openTime).toString()
            } else thh = (pv_take_hour.value + openTime).toString()
            tv_take_hour.text = thh
        }
        pv_take_minute.setOnValueChangedListener { numberPicker, i, j ->
            tnummm = pv_take_minute.value
            if (pv_take_minute.value < 10) {
                tmm = "0" + pv_take_minute.value.toString()
            } else tmm = pv_take_minute.value.toString()
            tv_take_minute.text = tmm
        }

//        btn_store_time.setBackgroundColor(Color.parseColor("#4C64FD"))

        //버튼클릭시~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        setOnClickListner()
    }

    private fun datesFromCalender(): Array<String> {
        val c1 = Calendar.getInstance()
        val dates = ArrayList<String>()
        val dateFormat = SimpleDateFormat("MMM dd일 (EE)")

        var notOffDay: Boolean = true
        for (i in 0..offdaySize) {
            if (weekFormat.format(c1.time) == offday[i]) {
                dates.add(dateFormat.format(c1.time) + " (휴무)")
                notOffDay = false
                break
            } else notOffDay = true
        }
        if (notOffDay == true) {
            dates.add(dateFormat.format(c1.time))
        }

        for (i in 0..59) {
            notOffDay = true
            c1.add(Calendar.DATE, 1)
            if(offdaySize>=0)
            for (i in 0..offdaySize) {
                if (weekFormat.format(c1.time) == offday[i]) {
                    dates.add(dateFormat.format(c1.time) + " (휴무)")
                    notOffDay = false
                    break
                } else {
                    notOffDay = true
                }
            }
            if (notOffDay == true) {
                dates.add(dateFormat.format(c1.time))
            }
        }
        return dates.toTypedArray()
    }

    private fun hourFromCalender(): Array<String> {
        val dates = ArrayList<String>()
        if (openTime < 10) {
            for (i in openTime..9) dates.add("0" + i)
        } else {
            for (i in 0..9) dates.add("0" + i)
        }
        for (i in 10..closeTime) dates.add(i.toString())
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
        var rightNow = Calendar.getInstance()
        var currentHour = rightNow.get(Calendar.HOUR_OF_DAY)
        var ttoast: Toast
        var notOffDay : Boolean = false


        btn_store_time.setOnClickListener {

            setStoreClickViewChange()

            Log.d("TAGGGGG",storeProhibit + "asd"+takeProhibit)
            notOffDay = true

            for(i in 0..offdaySize){
                if(takeProhibit == offday[i]) {
                    notOffDay = false
                    Log.d("TAGGGGG","^^^^^")
                    break
                }
            }
            if (svalue == 0 && snumhh < currentHour) {
                ttoast = Toast.makeText(context, "현재 시간 이후로 설정해주세요.", Toast.LENGTH_LONG)
                ttoast.setGravity(Gravity.CENTER, 0, 0)
                ttoast.show()
            } else {
                if (alertFlag == 1 && notOffDay==true) {
                    vs_custom_date_picker.showNext()
                    alertFlag = 0
                    //btn_store_time.setBackgroundColor(Color.parseColor(R.color.mainColor.toString()))
                    btn_take_time.setBackgroundColor(Color.parseColor("#ffffff"))
                } else {
                    var ttoast: Toast = Toast.makeText(context, "    상가 영업시간이 아닙니다.\n예약 시간을 다시 설정해주세요.", Toast.LENGTH_LONG)
                    ttoast.setGravity(Gravity.CENTER, 0, 0)
                    ttoast.show()
                }
            }
        }

        btn_take_time.setOnClickListener {

            setTakeClickViewChange()

            Log.d("TAGGGGG",storeProhibit  +"asd"+takeProhibit)
            notOffDay = true
            for(i in 0..offdaySize){
                if(storeProhibit == offday[i]) {
                    notOffDay = false
                    Log.d("TAGGGGG","&&&&&&&&&&&&&&")
                    break
                }
            }
            if (svalue == 0 && snumhh < currentHour) {
                Log.d("TAGGG",svalue.toString()+"asd"+snumhh+"asd"+currentHour)
                ttoast = Toast.makeText(context, "현재 시간 이후로 설정해주세요.", Toast.LENGTH_LONG)
                ttoast.setGravity(Gravity.CENTER, 0, 0)
                ttoast.show()
            } else {
                if (alertFlag == 0 && notOffDay == true) {
                    vs_custom_date_picker.showNext()
                    alertFlag = 1;
//                    btn_take_time.setBackgroundColor(Color.parseColor("#4C64FD"))
//                    btn_store_time.setBackgroundColor(Color.parseColor("#ffffff"))
                } else {
                    ttoast = Toast.makeText(context, "    상가 영업시간이 아닙니다.\n예약 시간을 다시 설정해주세요.", Toast.LENGTH_LONG)
                    ttoast.setGravity(Gravity.CENTER, 0, 0)
                    ttoast.show()
                }
            }
        }

        btn_time_confirm.setOnClickListener {
            notOffDay = true

            for(i in 0..offdaySize){
                if(takeProhibit == offday[i]) {
                    notOffDay = false
                    break
                }
            }
            if (notOffDay == false) {
                var ttoast: Toast = Toast.makeText(context, "    상가 영업시간이 아닙니다.\n예약 시간을 다시 설정해주세요.", Toast.LENGTH_LONG)
                ttoast.setGravity(Gravity.CENTER, 0, 0)
                ttoast.show()
                notOffDay = true
            } else {
                if (svalue == 0 && snumhh < currentHour) {
                    ttoast = Toast.makeText(context, "현재 시간 이후로 설정해주세요.", Toast.LENGTH_LONG)
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
                                (ctx as MainActivity).getTimeSettingDialog(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm, svalue, tvalue,storeIdx)
//                                MapMorePreviewFragment.getInstance(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm, svalue, tvalue)
                                dismiss()
                            }
                        } else {
                            (ctx as MainActivity).getTimeSettingDialog(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm, svalue, tvalue,storeIdx)
//                            MapMorePreviewFragment.getInstance(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm, svalue, tvalue)
//                            (ctx as MapMorePreviewFragment).getTimeSettingDialog(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm, svalue, tvalue)
                            dismiss()
                        }
                    } else {
//                        (ctx as MapMorePreviewFragment).getTimeSettingDialog(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm, svalue, tvalue)
                        (ctx as MainActivity).getTimeSettingDialog(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm, svalue, tvalue,storeIdx)
//                        (ctx as MapMorePreviewFragment).getTimeSettingDialog(smmddee.toString(), snumhh, snummm, tmmddee.toString(), tnumhh, tnummm, svalue, tvalue)
                        dismiss()
                    }
                }
            }
        }

    }

    private fun setTakeClickViewChange() {

        // 맡기는 시간
        btn_store_time.setBackgroundResource(R.drawable.bt_white_box_time)
        iv_store_time_image.setColorFilter(Color.parseColor("#262626"))
        tv_store_time_text.setTextColor(Color.parseColor("#494949"))
        tv_store_date.setTextColor(Color.parseColor("#494949"))
        tv_store_hour.setTextColor(Color.parseColor("#262626"))
        tv_store_colon.setTextColor(Color.parseColor("#262626"))
        tv_store_minute.setTextColor(Color.parseColor("#262626"))

        // 찾는 시간
        btn_take_time.setBackgroundResource(R.drawable.bt_full_box_time)
        iv_take_time_image.setColorFilter(Color.parseColor("#ffffff"))
        tv_take_time_text.setTextColor(Color.parseColor("#ffffff"))
        tv_take_date.setTextColor(Color.parseColor("#ffffff"))
        tv_take_hour.setTextColor(Color.parseColor("#ffffff"))
        tv_take_colon.setTextColor(Color.parseColor("#ffffff"))
        tv_take_minute.setTextColor(Color.parseColor("#ffffff"))
    }

    private fun setStoreClickViewChange() {

        // 맡기는 시간
        btn_store_time.setBackgroundResource(R.drawable.bt_full_box_time)
        iv_store_time_image.setColorFilter(Color.parseColor("#ffffff"))
        tv_store_time_text.setTextColor(Color.parseColor("#ffffff"))
        tv_store_date.setTextColor(Color.parseColor("#ffffff"))
        tv_store_hour.setTextColor(Color.parseColor("#ffffff"))
        tv_store_colon.setTextColor(Color.parseColor("#ffffff"))
        tv_store_minute.setTextColor(Color.parseColor("#ffffff"))

        // 찾는 시간
        btn_take_time.setBackgroundResource(R.drawable.bt_white_box_time)
        iv_take_time_image.setColorFilter(Color.parseColor("#262626"))
        tv_take_time_text.setTextColor(Color.parseColor("#494949"))
        tv_take_date.setTextColor(Color.parseColor("#494949"))
        tv_take_hour.setTextColor(Color.parseColor("#262626"))
        tv_take_colon.setTextColor(Color.parseColor("#262626"))
        tv_take_minute.setTextColor(Color.parseColor("#262626"))
    }

}