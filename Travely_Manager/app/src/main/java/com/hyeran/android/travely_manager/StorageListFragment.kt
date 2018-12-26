package com.hyeran.android.travely_manager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reserve_list.view.*
import kotlinx.android.synthetic.main.fragment_storage_list.view.*

class StorageListFragment : Fragment() {

    lateinit var storageListRVAdapter : StorageListRVAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_storage_list, container, false)

        var dataList : ArrayList<StorageListTempData> = ArrayList()
        dataList.add(StorageListTempData(0, "박상영", 8900, 2, "오후", 7, 40))
        dataList.add(StorageListTempData(0, "최유성", 8900, 2, "오후", 7, 40))
        dataList.add(StorageListTempData(0, "최정연", 8900, 2, "오후", 7, 40))

        storageListRVAdapter = StorageListRVAdapter(context, dataList)
        v.rv_storage_list_storage_list.adapter = storageListRVAdapter
        v.rv_storage_list_storage_list.layoutManager = LinearLayoutManager(context)

        return v
    }


}
