package com.hyeran.android.travely_manager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reserve_storage_list.view.*

class ReserveStorageListFragment : Fragment() {
    lateinit var reserveListRVAdapter : ReserveListRVAdapter
    lateinit var storageListRVAdapter: StorageListRVAdapter
    lateinit var r_dataList : ArrayList<ReserveListTempData>
    lateinit var s_dataList : ArrayList<StorageListTempData>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v =  inflater.inflate(R.layout.fragment_reserve_storage_list, container, false)

        reserveListRVAdapter = ReserveListRVAdapter(context, r_dataList)
        v.rv_reserve_list.adapter = reserveListRVAdapter
        v.rv_reserve_list.layoutManager = LinearLayoutManager(context)

        storageListRVAdapter = StorageListRVAdapter(context, s_dataList)
        v.rv_storage_list.adapter = storageListRVAdapter
        v.rv_storage_list.layoutManager = LinearLayoutManager(context)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        r_setRecyclerView()
        s_setRecyclerView()

//        var dataList : ArrayList<StorageListTempData> = ArrayList()
//        dataList.add(StorageListTempData(0, "박상영", 8900, 2, "오후", 7, 40))
//        dataList.add(StorageListTempData(0, "최유성", 8900, 2, "오후", 7, 40))
//        dataList.add(StorageListTempData(0, "최정연", 8900, 2, "오후", 7, 40))
//
//        storageListRVAdapter = StorageListRVAdapter(context, dataList)
//        v.rv_storage_list_storage_list.adapter = storageListRVAdapter
//        v.rv_storage_list_storage_list.layoutManager = LinearLayoutManager(context)
    }

    private fun r_setRecyclerView() {
        // 임시 데이터 1
        r_dataList.add(ReserveListTempData(0, "박상영","결제완료", "2018.10.23", 8900, 2, "19:45"))
    }

    private fun s_setRecyclerView() {
        // 임시 데이터 2
        s_dataList.add(StorageListTempData(0, "박상영","결제완료", "2018.10.23", 8900, 2, "19:45"))
        s_dataList.add(StorageListTempData(0, "박상영","결제완료", "2018.10.23", 8900, 2, "19:45"))
    }
}