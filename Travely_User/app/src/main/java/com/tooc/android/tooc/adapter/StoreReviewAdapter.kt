package com.tooc.android.tooc.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tooc.android.tooc.R
import com.tooc.android.tooc.model.store.ReviewResponseData
import java.sql.Timestamp

class StoreReviewAdapter(val ctx : Context, val dataList : ArrayList<ReviewResponseData>)
    : RecyclerView.Adapter<StoreReviewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // 뷰 인플레이트!!
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_review, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 뷰 바인딩!!
        holder.name.text = dataList[position].userName
        holder.grade.text = dataList[position].like.toString()
        var currentTime : Long = System.currentTimeMillis()
        var createdAtTime : Long = dataList[position].createdAt
        holder.time.text = Timestamp(currentTime-createdAtTime).hours.toString()
        holder.content.text = dataList[position].content
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val reviewer : ImageView = itemView.findViewById(R.id.iv_rv_item_reviewer) as ImageView
        val name : TextView = itemView.findViewById(R.id.tv_rv_item_review_name) as TextView
        val time : TextView = itemView.findViewById(R.id.tv_hour_rv_item) as TextView
        val grade : TextView = itemView.findViewById(R.id.tv_rv_item_review_grade) as TextView
        val content : TextView = itemView.findViewById(R.id.tv_rv_item_review_content) as TextView
    }
}