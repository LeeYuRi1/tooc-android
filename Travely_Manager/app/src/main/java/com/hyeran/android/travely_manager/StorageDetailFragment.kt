package com.hyeran.android.travely_manager


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_storage_detail.view.*

class StorageDetailFragment : Fragment() {

    lateinit var photoStorageDetailRVAdapter: PhotoStorageDetailRVAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_storage_detail, container, false)

        var dataList : ArrayList<PhotoStorageDetailTempData> = ArrayList()
        dataList.add(PhotoStorageDetailTempData(0))
        dataList.add(PhotoStorageDetailTempData(0))
//        dataList.add(PhotoStorageDetailTempData(0))
//
//        photoStorageDetailRVAdapter = PhotoStorageDetailRVAdapter(context, dataList)
//        v.rv_storage_detail.adapter = photoStorageDetailRVAdapter
//        v.rv_storage_detail.layoutManager = LinearLayoutManager(context)



        return v
    }
}
