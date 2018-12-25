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
        holder.myreview_name.text = dataList[position].myreviewname
        holder.myreview_date.text = dataList[position].myreviewdate.toString()
        holder.myreview_content.text = dataList[position].myreviewcontent
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var myreview_name: TextView = itemView.findViewById(R.id.tv_name_myreview) as TextView
        var myreview_date: TextView = itemView.findViewById(R.id.tv_date_myreview) as TextView
        var myreview_content : TextView = itemView.findViewById(R.id.tv_content_myreview) as TextView
    }
}