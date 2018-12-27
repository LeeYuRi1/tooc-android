package com.hyeran.android.travely_user.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.data.MypageMyReviewData
import java.util.ArrayList

class MypageMyReviewAdapter(val ctx: Context, val dataList : ArrayList<MypageMyReviewData>) : RecyclerView.Adapter<MypageMyReviewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.item_myreview , parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.store_name.text = dataList[position].storename
        holder.store_area.text = dataList[position].storearea
        holder.review_year.text = dataList[position].year
        holder.review_month.text = dataList[position].month
        holder.review_day.text = dataList[position].day
        holder.review_content.text = dataList[position].content
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var store_name: TextView = itemView.findViewById(R.id.tv_name_myreview) as TextView
        var store_area: TextView = itemView.findViewById(R.id.tv_area_myreview) as TextView
        var review_year : TextView = itemView.findViewById(R.id.tv_year_myreview) as TextView
        var review_month : TextView = itemView.findViewById(R.id.tv_month_myreview) as TextView
        var review_day : TextView = itemView.findViewById(R.id.tv_day_myreview) as TextView
        var review_content : TextView = itemView.findViewById(R.id.tv_content_myreview) as TextView

    }
}