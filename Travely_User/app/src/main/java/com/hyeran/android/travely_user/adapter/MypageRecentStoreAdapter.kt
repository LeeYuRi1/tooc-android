package com.hyeran.android.travely_user.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.R.id.constraint_recentstore
import com.hyeran.android.travely_user.data.MypageRecentStoreData
import com.hyeran.android.travely_user.join.RecentstoreDetailFragment
import com.hyeran.android.travely_user.map.LocationListActivity
import com.hyeran.android.travely_user.model.StoreInfoResponseData
import com.hyeran.android.travely_user.mypage.MypageFragment
import com.hyeran.android.travely_user.mypage.WriteReviewDialog
import java.sql.Time
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

        var recentStartTime : Long =dataList[position].openTime
        //var a =Timestamp(recentStartTime).
        //var b =Timestamp(recentStartTime).minutes

        //Log.d("TAGGGG",a.toString()+"^^^^^"+b)

        Log.d("TAG URI",dataList[position].storeImage)

        holder.recent_name.text = dataList[position].storeName
        holder.recent_addr.text = dataList[position].address
        //holder.recent_ampm.text = dataList[position].storetime
        holder.recent_start.text = dataList[position].openTime.toString()
        holder.recent_end.text = dataList[position].closeTime.toString()

        holder.reviewwrite.setOnClickListener {
            WriteReviewDialog(ctx).show()
        }
        holder.recent_detail.setOnClickListener {
            //(ctx as MypageFragment).replaceFragment(RecentstoreDetailFragment())
        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recent_image: ImageView = itemView.findViewById(R.id.iv_storeimage_recentstore) as ImageView
        var recent_name: TextView = itemView.findViewById(R.id.tv_name_recentstore) as TextView
        var recent_addr: TextView = itemView.findViewById(R.id.tv_addr_recentstore) as TextView
        var recent_ampm: TextView = itemView.findViewById(R.id.tv_time_recentstore) as TextView
        var recent_start: TextView = itemView.findViewById(R.id.tv_starttime_recentstore) as TextView
        var recent_end: TextView = itemView.findViewById(R.id.tv_endtime_recentstore) as TextView

        var reviewwrite: Button = itemView.findViewById(R.id.btn_reviewwrite_recentstore) as Button
        var recent_detail: ConstraintLayout = itemView.findViewById(R.id.constraint_recentstore) as ConstraintLayout
    }

}

