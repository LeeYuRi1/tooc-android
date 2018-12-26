package com.hyeran.android.travely_manager.mypage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.travely_manager.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_whole_statistics.*
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.utils.ColorTemplate


class WholeStatisticsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_whole_statistics, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        DrawLinechart()
    }

    fun DrawLinechart() {
        var lineChart : LineChart = linechart_whole_statistics

        val entries : ArrayList<Entry> = ArrayList()
        entries.add(Entry(0f, 8f))
        entries.add(Entry(1f, 20f))
        entries.add(Entry(2f, 30f))
        entries.add(Entry(3f, 25f))
        entries.add(Entry(4f, 35f))
        entries.add(Entry(5f, 30f))
        entries.add(Entry(6f, 10f))
        entries.add(Entry(7f, 20f))
        entries.add(Entry(8f, 35f))
        entries.add(Entry(9f, 40f))
        entries.add(Entry(10f, 30f))
        entries.add(Entry(11f, 11f))

        var dataset = LineDataSet(entries, "")

        //****
        // Controlling X axis
        val xAxis = lineChart.getXAxis()
        // Set the xAxis position to bottom. Default is top
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        //Customizing x axis value
        val months = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9","10", "11", "12")

        val formatter = IAxisValueFormatter { value, axis -> months[value.toInt()] }
        xAxis.setGranularity(1.0f) // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter)
        xAxis.setLabelCount(12)

        //***
        // Controlling right side of y axis
        val yAxisRight = lineChart.getAxisRight()
        yAxisRight.setEnabled(false)

        val money = arrayOf("10만원", "20만원", "30만원", "40만원")

        //***
        // Controlling left side of y axis
        val yAxisLeft = lineChart.getAxisLeft()
        yAxisLeft.setGranularity(1f)

        // Setting Data
        val data = LineData(dataset)
        //dataset.setDrawCubic(true);

        //선 색깔
        dataset.setColor(Color.BLUE)
        //원 색깔
        dataset.setCircleColor(Color.BLUE)



        lineChart.setData(data)
        lineChart.animateX(2500)
        //refresh
        lineChart.invalidate()

    }
}