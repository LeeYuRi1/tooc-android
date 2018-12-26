package com.hyeran.android.travely_manager

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class PhotoStorageDetailRVAdapter(val ctx : Context?, val dataList : ArrayList<PhotoStorageDetailTempData>) : RecyclerView.Adapter<PhotoStorageDetailRVAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.item_photo_storage_detail, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.photo.setImageResource(dataList[position].photo)
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val photo : ImageView = itemView.findViewById(R.id.iv_photo_storage_detail)
    }
}

