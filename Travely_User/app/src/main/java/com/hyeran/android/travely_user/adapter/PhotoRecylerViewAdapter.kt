package com.hyeran.android.travely_user.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.hyeran.android.travely_user.data.PhotoData
import com.hyeran.android.travely_user.R

class PhotoRecylerViewAdapter (val ctx : Context, val dataList : ArrayList<PhotoData>)
    : RecyclerView.Adapter<PhotoRecylerViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // 뷰 인플레이트!!
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_photo, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 뷰 바인딩!!
        if (dataList != null) {
            Glide.with(ctx)
                    .load(R.drawable.filter1)
                    .into(holder.photo)
        } else {
            Glide.with(holder!!.itemView.context)
                    .load(R.drawable.filter1)
                    .into(holder.photo)
        }

    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val photo : ImageView = itemView!!.findViewById(R.id.iv_rv_item_photo) as ImageView
    }
}