package com.hyeran.android.travely_user.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.data.MypageRecentStoreData
import java.util.ArrayList

class MypageRecentStoreAdapter(val ctx: Context, val dataList : ArrayList<MypageRecentStoreData>) : RecyclerView.Adapter<MypageRecentStoreAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.item_recentstore , parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.recent_name.text = dataList[position].storename
        holder.recent_addr.text = dataList[position].storeaddr
        holder.recent_time.text = dataList[position].storetime
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var recent_image : ImageView = itemView.findViewById(R.id.iv_storeimage_recentstore) as ImageView
        var recent_name : TextView = itemView.findViewById(R.id.tv_name_recentstore) as TextView
        var recent_addr : TextView = itemView.findViewById(R.id.tv_addr_recentstore) as TextView
        var recent_time : TextView = itemView.findViewById(R.id.tv_time_recentstore) as TextView
    }
}

