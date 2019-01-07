package com.hyeran.android.tooc.adapter

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hyeran.android.tooc.MainActivity
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.data.MypageLikeData
import com.hyeran.android.tooc.map.LocationListActivity
import com.hyeran.android.tooc.model.mypage.SimpleStoreResponseDtosData
import com.hyeran.android.tooc.mypage.LikeFragment
import java.sql.Timestamp
import java.util.ArrayList

class MypageLikeAdapter(val ctx: Context, val dataList: ArrayList<SimpleStoreResponseDtosData>) : RecyclerView.Adapter<MypageLikeAdapter.Holder>() {

    var likeFragment = LikeFragment()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_like, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        dataList[position].storeIdx
        likeFragment.getLikeStoreIdx(dataList[position].storeIdx)

        Glide.with(holder!!.itemView.context)
                .load(dataList[position].storeImgUrl)
                .into(holder!!.like_image)
        holder.like_name.text = dataList[position].storeName
        holder.like_addr.text = dataList[position].address
        holder.like_rating.rating = dataList[position].grade


        var likeStartTime : Long =dataList[position].openTime
        if(Timestamp(likeStartTime).hours.toString().trim().length == 1){
            holder.like_start_hour.text = "0" + Timestamp(likeStartTime).hours.toString().trim()
        } else {
            holder.like_start_hour.text = Timestamp(likeStartTime).hours.toString().trim()
        }
        if(Timestamp(likeStartTime).minutes.toString().trim().length == 1){
            holder.like_start_minute.text = "0" + Timestamp(likeStartTime).minutes.toString().trim()
        } else {
            holder.like_start_minute.text = Timestamp(likeStartTime).minutes.toString().trim()
        }

        var likeEndTime : Long =dataList[position].closeTime
        if(Timestamp(likeEndTime).hours.toString().trim().length == 1){
            holder.like_end_hour.text = "0" + Timestamp(likeEndTime).hours.toString().trim()
        } else {
            holder.like_end_hour.text = Timestamp(likeEndTime).hours.toString().trim()
        }
        if(Timestamp(likeEndTime).minutes.toString().trim().length == 1){
            holder.like_end_minute.text = "0" + Timestamp(likeEndTime).minutes.toString().trim()
        } else {
            holder.like_end_minute.text = Timestamp(likeEndTime).minutes.toString().trim()
        }

        holder.like_heart.isSelected = true

        holder.like_heart.setOnClickListener {
            holder.like_heart.isSelected = false
        }



    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var like_image : ImageView = itemView.findViewById(R.id.iv_storeimage_like) as ImageView
        var like_name: TextView = itemView.findViewById(R.id.tv_name_like) as TextView
        var like_addr: TextView = itemView.findViewById(R.id.tv_addr_like) as TextView
        var like_heart: ImageView = itemView.findViewById(R.id.iv_heart_like) as ImageView
        var like_rating : RatingBar = itemView.findViewById(R.id.ratingBar_like) as RatingBar

        var like_start_hour: TextView = itemView.findViewById(R.id.tv_starttime_hour_like) as TextView
        var like_start_minute: TextView = itemView.findViewById(R.id.tv_starttime_minute_like) as TextView
        var like_end_hour: TextView = itemView.findViewById(R.id.tv_endtime_hour_like) as TextView
        var like_end_minute: TextView = itemView.findViewById(R.id.tv_endtime_minute_like) as TextView

    }
}