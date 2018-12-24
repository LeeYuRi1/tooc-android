package com.hyeran.android.travely_user

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class ReviewRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<ReviewData>)
    : RecyclerView.Adapter<ReviewRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // 뷰 인플레이트!!
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_review, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 뷰 바인딩!!
        holder.name.text = dataList[position].name
        holder.time.text = dataList[position].time.toString()
        holder.grade.text = dataList[position].grade.toString()
        holder.content.text = dataList[position].content
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val reviewer : ImageView = itemView.findViewById(R.id.iv_rv_item_reviewer) as ImageView
        val name : TextView = itemView.findViewById(R.id.tv_rv_item_review_name) as TextView
        val time : TextView = itemView.findViewById(R.id.tv_rv_item_review_time) as TextView
        val grade : TextView = itemView.findViewById(R.id.tv_rv_item_review_grade) as TextView
        val content : TextView = itemView.findViewById(R.id.tv_rv_item_review_content) as TextView
    }
}