package com.hyeran.android.travely_user.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.data.MypageLikeData
import java.util.ArrayList

class MypageLikeAdapter(val ctx: Context, val dataList: ArrayList<MypageLikeData>) : RecyclerView.Adapter<MypageLikeAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_like, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.like_name.text = dataList[position].likename
        holder.like_addr.text = dataList[position].likeaddr
        holder.like_heart.isSelected = true

        if(!dataList[position].likeheart) {
            holder.like_heart.visibility = View.GONE
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var recent_image : ImageView = itemView.findViewById(R.id.iv_storeimage_recentstore) as ImageView
        var like_name: TextView = itemView.findViewById(R.id.tv_name_like) as TextView
        var like_addr: TextView = itemView.findViewById(R.id.tv_addr_like) as TextView
        var like_heart: ImageView = itemView.findViewById(R.id.iv_heart_like) as ImageView

    }
}