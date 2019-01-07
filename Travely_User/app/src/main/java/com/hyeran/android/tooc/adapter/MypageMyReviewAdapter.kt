package com.hyeran.android.tooc.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hyeran.android.tooc.model.mypage.ReviewLookupData
import java.text.SimpleDateFormat
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.data.MypageMyReviewData
import java.util.ArrayList

class MypageMyReviewAdapter(val ctx: Context, val dataList : ArrayList<ReviewLookupData>)
    : RecyclerView.Adapter<MypageMyReviewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.item_myreview , parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        Glide.with(holder!!.itemView.context)
                .load(dataList[position].storeImgUrl)
                .into(holder!!.store_image)

        holder.store_name.text = dataList[position].storeName
        holder.store_area.text = dataList[position].address

//        holder.review_year.text = dataList[position].year
//        holder.review_month.text = dataList[position].month
//        holder.review_day.text = dataList[position].day

        var createDate : Long = dataList[position].createAt
        var simpleDate = SimpleDateFormat("yyyy.MM.dd")
        holder.review_ymd.text = simpleDate.format(createDate)

        holder.review_content.text = dataList[position].content

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val store_image : ImageView = itemView.findViewById(R.id.iv_storeimage_myreview) as ImageView
        val store_name: TextView = itemView.findViewById(R.id.tv_name_myreview) as TextView
        val store_area: TextView = itemView.findViewById(R.id.tv_area_myreview) as TextView

        /*
        var review_year : TextView = itemView.findViewById(R.id.tv_year_myreview) as TextView
        var review_month : TextView = itemView.findViewById(R.id.tv_month_myreview) as TextView
        var review_day : TextView = itemView.findViewById(R.id.tv_day_myreview) as TextView
        */

        var review_ymd : TextView = itemView.findViewById(R.id.tv_ymd_myreview) as TextView
        val review_content : TextView = itemView.findViewById(R.id.tv_content_myreview) as TextView
    }
}