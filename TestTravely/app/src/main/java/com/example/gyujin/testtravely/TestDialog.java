package com.example.gyujin.testtravely

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast

import java.text.SimpleDateFormat
import java.util.*

class TestDialog : DialogFragment(), View.OnClickListener {

        internal var dates: Array<String>? = null
        internal var datePicker: NumberPicker? = null
        internal var hourPicker: NumberPicker? = null
        internal var minutePicker: NumberPicker? = null
        internal var doneButton: Button? = null
        internal var currentTimeButton: Button? = null
        internal var rightNow = Calendar.getInstance()

private val datesFromCalender: Array<String>
        get() {
                val c1 = Calendar.getInstance()
                val c2 = Calendar.getInstance()

                val dates = ArrayList<String>()
        val dateFormat = SimpleDateFormat("EEE, dd MMM")
        dates.add(dateFormat.format(c1.getTime()))

        for (i in 0..59) {
        c1.add(Calendar.DATE, 1)
        dates.add(dateFormat.format(c1.getTime()))
        }
        c2.add(Calendar.DATE, -60)

        for (i in 0..59) {
        c2.add(Calendar.DATE, 1)
        dates.add(dateFormat.format(c2.getTime()))
        }
        return dates.toTypedArray()
        }

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        dates = datesFromCalender

        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)


        val getDialog = Dialog(getActivity())
        val view = inflater.inflate(R.layout.testtsttesttesttesttest, container)
        datePicker = view.findViewById(R.id.datePicker)
        hourPicker = view.findViewById(R.id.hourPicker)
        minutePicker = view.findViewById(R.id.minutePicker)
        datePicker!!.minValue = 0
        datePicker!!.maxValue = dates!!.size - 1
        datePicker!!.setFormatter { value -> dates!![value] }

        datePicker!!.displayedValues = dates
        hourPicker!!.minValue = 0
        hourPicker!!.maxValue = 23
        hourPicker!!.value = rightNow.get(Calendar.HOUR_OF_DAY)
        minutePicker!!.minValue = 0
        minutePicker!!.maxValue = 59
        minutePicker!!.value = rightNow.get(Calendar.MINUTE)
        doneButton!!.setOnClickListener(this)
        currentTimeButton!!.setOnClickListener(this)
        getDialog.setTitle("choose_time")
        return view
        }


        }