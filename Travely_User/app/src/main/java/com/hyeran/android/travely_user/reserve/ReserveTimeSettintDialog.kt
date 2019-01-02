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
import com.hyeran.android.travely_user.R.id.vs_custom_date_picker
import kotlinx.android.synthetic.main.dialog_reserve_time_setting.*
import kotlinx.android.synthetic.main.dialog_reserve_time_setting.view.*
import kotlinx.android.synthetic.main.fragment_faq.view.*
import org.jetbrains.anko.support.v4.toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ReserveTimeSettintDialog(val ctx: Context?) : Dialog(ctx) {


    //쉬는날
    var offday: String? = "일"

    var dates: Array<String>? = null
    var rightNow = Calendar.getInstance()
    val weekFormat = SimpleDateFormat("E")

    var tcalen = Calendar.getInstance()
    var scalen = Calendar.getInstance()

    val dateformat = SimpleDateFormat("MMM dd일 (EE)")
    val calDateFormat = SimpleDateFormat("yy MMM dd일 ")
    val allFormat = SimpleDateFormat("yy MMM dd일 kk:mm")

    var storeProhibit: String? = null
    var takeProhibit: String? = null

    var allstore: String? = null
    var alltake: String? = null


    var smmddee : String? = null
    var tmmddee : String? = null
    var shh : String? = null
    var smm : String? = null
    var thh : String? = null
    var tmm : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_reserve_time_setting)

        //val v = inflater.inflate(R.layout.dialog_reserve_time_setting, container, false)
        // datesFromCalender()
        dates = datesFromCalender()


        //val textDate = dateformat.format(calen.time)

        tv_store_date.text = dateformat.format(scalen.time)
        tv_take_date.text = dateformat.format(tcalen.time)
        storeProhibit = weekFormat.format(scalen.time)
        takeProhibit = weekFormat.format(tcalen.time)
        allstore = allFormat.format(scalen.time)
        alltake = allFormat.format(tcalen.time)

        //store에서 피커뷰 사용~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        pv_store_date.minValue = 0
        pv_store_date.maxValue = dates!!.size - 1
        pv_store_date.displayedValues = dates

        pv_store_hour.minValue = 0
        pv_store_hour.maxValue = 23
        pv_store_hour.value = rightNow.get(Calendar.HOUR_OF_DAY)
        tv_store_hour.text = pv_store_hour.value.toString()

        pv_store_minute.minValue = 0
        pv_store_minute.maxValue = 59
        pv_store_minute.value = rightNow.get(Calendar.MINUTE)
        tv_store_minute.text = pv_store_minute.value.toString()


        pv_store_date.wrapSelectorWheel = false

        smmddee = dateformat.format(scalen.time)

        smmddee = dateformat.format(scalen.time)


        pv_store_date.setOnValueChangedListener { numberPicker, i, j ->
            scalen.add(Calendar.DATE, pv_store_date.value)
            tv_store_date.text = dateformat.format(scalen.time)
            smmddee = dateformat.format(scalen.time)
            storeProhibit = weekFormat.format(scalen.time).toString()

            scalen.add(Calendar.DATE, -pv_store_date.value)
        }

        pv_store_hour.setOnValueChangedListener { numberPicker, i, j ->
            tv_store_hour.text = pv_store_hour.value.toString()
            shh = pv_store_hour.value.toString()
//            storeDate = calDateFormat.parse(v.tv_store_date.text.toString()+v.tv_store_hour.text.toString()+v.tv_store_minute.text.toString())

        }
        pv_store_minute.setOnValueChangedListener { numberPicker, i, j ->
            tv_store_minute.text = pv_store_minute.value.toString()
            smm = pv_store_minute.value.toString()
//            storeDate = calDateFormat.parse(v.tv_store_date.text.toString()+v.tv_store_hour.text.toString()+v.tv_store_minute.text.toString())

        }


        //take피커뷰 사용~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        pv_take_date.minValue = 0
        pv_take_date.maxValue = dates!!.size - 1
        //   v.custom_picker_date_full.setFormatter(NumberPicker.Formatter { value -> dates!![value] })
        pv_take_date.displayedValues = dates


        pv_take_hour.minValue = 0
        pv_take_hour.maxValue = 23
        pv_take_hour.value = rightNow.get(Calendar.HOUR_OF_DAY + 4)
        tv_take_hour.text = pv_take_hour.value.toString()

        pv_take_minute.minValue = 0
        pv_take_minute.maxValue = 59
        pv_take_minute.value = rightNow.get(Calendar.MINUTE)
        tv_take_minute.text = pv_take_minute.value.toString()


        pv_take_date.wrapSelectorWheel = false

        pv_take_date.setOnValueChangedListener { numberPicker, i, j ->
            tcalen.add(Calendar.DATE, pv_take_date.value)
            takeProhibit = weekFormat.format(tcalen.time).toString()
            tmmddee = dateformat.format(tcalen.time)
//
//            takeString = calDateFormat.format(tcalen.time)
//            takeDate = allFormat.parse(takeString)

            // Toast.makeText(context,takeDate.toString(),Toast.LENGTH_LONG).show()
            tv_take_date.text = dateformat.format(tcalen.time)

            tcalen.add(Calendar.DATE, -pv_take_date.value)
        }
        pv_take_hour.setOnValueChangedListener { numberPicker, i, j ->
            tv_take_hour.text = pv_take_hour.value.toString()
            thh = pv_take_hour.value.toString()
        }
        pv_take_minute.setOnValueChangedListener { numberPicker, i, j ->
            tv_take_minute.text = pv_take_minute.value.toString()
            tmm =  pv_take_minute.value.toString()
        }

        btn_store_time.setBackgroundColor(Color.parseColor("#4C64FD"))


        //버튼클릭시~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        setOnClickListner()

    }


    private fun datesFromCalender(): Array<String> {
        val c1 = Calendar.getInstance()

        val dates = ArrayList<String>()
        //val dateFormat = SimpleDateFormat("EEE, dd MMM")
        val dateFormat = SimpleDateFormat("MMM dd일 EE")


        if (weekFormat.format(c1.time) == offday) {
            dates.add(dateFormat.format(c1.time) + " (휴무)")
        } else {
            dates.add(dateFormat.format(c1.time))
        }

        for (i in 0..59) {
            c1.add(Calendar.DATE, 1)
            //해당 요일 빼기
            if (weekFormat.format(c1.time) == offday) {
                dates.add(dateFormat.format(c1.time) + " (휴무)")
            } else {
                dates.add(dateFormat.format(c1.time))
            }
        }

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
            //  toast((storeDate!!.time-takeDate!!.time).toString())

            if ((0 > pv_take_date.value - pv_store_date.value)) {
                if (0 > pv_take_hour.value - pv_store_hour.value) {
                        if (0 > pv_take_minute.value - pv_store_minute.value) {
                            Toast.makeText(context, "시간 설정이 잘못되었습니다.", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                (ctx as MainActivity).getTimeSettingDialog(smmddee.toString(),shh.toString(),smm.toString(),tmmddee.toString(),thh.toString(),tmm.toString())
                Toast.makeText(context, tv_store_hour.text, Toast.LENGTH_LONG).show()
                dismiss()
            }
            // toast(takeDate)!!.time.toString())
//            if((storeDate!!.time-takeDate!!.time)<0){
//                toast("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD")
//            }

        }

    }

}