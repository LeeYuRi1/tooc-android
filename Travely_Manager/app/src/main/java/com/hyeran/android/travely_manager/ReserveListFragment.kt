package com.hyeran.android.travely_manager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reserve_list.*
import kotlinx.android.synthetic.main.fragment_reserve_list.view.*


//class ReserveListFragment : Fragment() {
//
//    lateinit var reserveListRVAdapter : ReserveListRVAdapter
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val v = inflater.inflate(R.layout.fragment_reserve_list, container, false)
//
//        var dataList : ArrayList<ReserveListTempData> = ArrayList()
//        dataList.add(ReserveListTempData(0, "박상영", "미결제", 8900, 2, "오후", 7, 40))
//        dataList.add(ReserveListTempData(0, "최유성", "결제완료", 8900, 2, "오후", 7, 40))
//        dataList.add(ReserveListTempData(0, "최정연", "결제완료", 8900, 2, "오후", 7, 40))
//
//        reserveListRVAdapter = ReserveListRVAdapter(context, dataList)
//        v.rv_reserve_list_reserve_list.adapter = reserveListRVAdapter
//        v.rv_reserve_list_reserve_list.layoutManager = LinearLayoutManager(context)
//
//        return v
//    }
//
//
//}
