package com.tooc.android.tooc

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.tooc.android.tooc.model.PhotoData

class ImgUrlRVAdapter (val ctx : Context?, var dataList : ArrayList<PhotoData>):RecyclerView.Adapter<ImgUrlRVAdapter.holder>(){
   lateinit var view :View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgUrlRVAdapter.holder {
        view = LayoutInflater.from(ctx).inflate(R.layout.item_luggage_picture, parent, false)

        return holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ImgUrlRVAdapter.holder, position: Int) {
        Log.d("###",dataList[position].photo)

        Glide.with(ctx!!)
                .load(dataList[0].photo)
                .into(holder.photo)
//        holder.photo.setImageResource(dataList[position].bagImgUrl)
        Log.d("#####","oh yeah")
    }
    inner class holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var photo : ImageView = itemView.findViewById(R.id.iv_luggage_picture)
    }
}

