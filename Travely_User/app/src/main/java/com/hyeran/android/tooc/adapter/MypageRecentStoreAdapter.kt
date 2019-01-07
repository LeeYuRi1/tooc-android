package com.hyeran.android.tooc.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hyeran.android.tooc.MainActivity
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.join.RecentstoreDetailFragment
import com.hyeran.android.tooc.model.StoreInfoResponseData
import com.hyeran.android.tooc.dialog.WriteReviewDialog
import java.sql.Timestamp
import java.util.ArrayList

class MypageRecentStoreAdapter(val ctx: Context, val dataList: ArrayList<StoreInfoResponseData>) : RecyclerView.Adapter<MypageRecentStoreAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_recentstore, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        Glide.with(holder!!.itemView.context)
                .load(dataList[position].storeImage)
                .into(holder!!.recent_image)

        holder.recent_name.text = dataList[position].storeName
        holder.recent_addr.text = dataList[position].address

        holder.reviewwrite.setOnClickListener {
            WriteReviewDialog(ctx).show()
        }
        holder.recent_detail.setOnClickListener {
            (ctx as MainActivity).replaceFragment(RecentstoreDetailFragment())
        }


        var recentStartTime : Long =dataList[position].openTime

        if(Timestamp(recentStartTime).hours.toString().trim().length == 1){
            holder.recent_start_hour.text = "0" + Timestamp(recentStartTime).hours.toString().trim()
        } else {
            holder.recent_start_hour.text = Timestamp(recentStartTime).hours.toString().trim()
        }
        if(Timestamp(recentStartTime).minutes.toString().trim().length == 1){
            holder.recent_start_minute.text = "0" + Timestamp(recentStartTime).minutes.toString().trim()
        } else {
            holder.recent_start_minute.text = Timestamp(recentStartTime).minutes.toString().trim()
        }

        var recentEndTime : Long =dataList[position].closeTime
        if(Timestamp(recentEndTime).hours.toString().trim().length == 1){
            holder.recent_end_hour.text = "0" + Timestamp(recentEndTime).hours.toString().trim()
        } else {
            holder.recent_end_hour.text = Timestamp(recentEndTime).hours.toString().trim()
        }
        if(Timestamp(recentEndTime).minutes.toString().trim().length == 1){
            holder.recent_end_minute.text = "0" + Timestamp(recentEndTime).minutes.toString().trim()
        } else {
            holder.recent_end_minute.text = Timestamp(recentEndTime).minutes.toString().trim()
        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recent_image: ImageView = itemView.findViewById(R.id.iv_storeimage_recentstore) as ImageView
        var recent_name: TextView = itemView.findViewById(R.id.tv_name_recentstore) as TextView
        var recent_addr: TextView = itemView.findViewById(R.id.tv_addr_recentstore) as TextView
        var recent_start_hour: TextView = itemView.findViewById(R.id.tv_starttime_hour_recentstore) as TextView
        var recent_start_minute: TextView = itemView.findViewById(R.id.tv_starttime_minute_recentstore) as TextView
        var recent_end_hour: TextView = itemView.findViewById(R.id.tv_endtime_hour_recentstore) as TextView
        var recent_end_minute: TextView = itemView.findViewById(R.id.tv_endtime_minute_recentstore) as TextView
        var reviewwrite: Button = itemView.findViewById(R.id.btn_reviewwrite_recentstore) as Button
        var recent_detail: ConstraintLayout = itemView.findViewById(R.id.constraint_recentstore) as ConstraintLayout
    }

}

