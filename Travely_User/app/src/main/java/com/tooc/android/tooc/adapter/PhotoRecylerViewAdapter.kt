package com.tooc.android.tooc.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.tooc.android.tooc.data.PhotoData
import com.tooc.android.tooc.R
import com.tooc.android.tooc.R.id.iv_rv_item_photo
import com.tooc.android.tooc.model.store.StoreImageResponseData
import com.tooc.android.tooc.model.store.StoreResponseData

//class PhotoRecylerViewAdapter (val ctx : Context, val data : ArrayList<StoreImageResponseData>, val dataList : ArrayList<PhotoData>)
class PhotoRecylerViewAdapter (val ctx : Context, val dataList : ArrayList<StoreImageResponseData>)
    : RecyclerView.Adapter<PhotoRecylerViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // 뷰 인플레이트!!
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_photo, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 뷰 바인딩!!

        Glide.with(holder!!.itemView.context)
                .load(dataList[position].storeImg)
                .into(holder!!.photo)

    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val photo : ImageView = itemView!!.findViewById(R.id.iv_rv_item_photo) as ImageView
    }
}