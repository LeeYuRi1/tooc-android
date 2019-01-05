package com.hyeran.android.travely_user.adapter

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.data.MypageLikeData
import com.hyeran.android.travely_user.map.LocationListActivity
import com.hyeran.android.travely_user.model.mypage.SimpleStoreResponseDtosData
import com.hyeran.android.travely_user.mypage.LikeFragment
import java.util.ArrayList

class MypageLikeAdapter(val ctx: Context, val dataList: ArrayList<SimpleStoreResponseDtosData>) : RecyclerView.Adapter<MypageLikeAdapter.Holder>() {

    lateinit var likeFragment: LikeFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_like, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.like_name.text = dataList[position].storeName
        dataList[position].storeIdx

        (ctx as LikeFragment).getLikeStoreIdx(dataList[position].storeIdx)

//        var args = Bundle()
//        args.putStringArrayList("dataList",dataList[position].storeIdx)



//        likeFragment.getLikeStoreIdx(dataList[position].storeIdx)

        //holder.like_addr.text = dataList[position].likeaddr
//        holder.like_heart.isSelected = true
//
//        if(!dataList[position].likeheart) {
//            holder.like_heart.visibility = View.GONE
//        }

    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var like_name: TextView = itemView.findViewById(R.id.tv_name_like) as TextView
        var like_addr: TextView = itemView.findViewById(R.id.tv_addr_like) as TextView
        var like_heart: ImageView = itemView.findViewById(R.id.iv_heart_like) as ImageView

        var recent_start_hour: TextView = itemView.findViewById(R.id.tv_starttime_hour_like) as TextView
        var recent_start_minute: TextView = itemView.findViewById(R.id.tv_starttime_minute_like) as TextView
        var recent_end_hour: TextView = itemView.findViewById(R.id.tv_endtime_hour_like) as TextView
        var recent_end_minute: TextView = itemView.findViewById(R.id.tv_endtime_minute_like) as TextView

    }
}