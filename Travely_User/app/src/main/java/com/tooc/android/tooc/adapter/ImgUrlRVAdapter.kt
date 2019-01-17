package com.tooc.android.tooc.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.tooc.android.tooc.R
import com.tooc.android.tooc.model.PhotoData

class ImgUrlRVAdapter (val ctx : Context?, var dataList : ArrayList<PhotoData>) : RecyclerView.Adapter<ImgUrlRVAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.item_luggage_picture, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(ctx!!)
                .load(dataList[0].photo)
                .into(holder.photo)
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var photo : ImageView = itemView.findViewById(R.id.iv_luggage_picture)
    }
}

