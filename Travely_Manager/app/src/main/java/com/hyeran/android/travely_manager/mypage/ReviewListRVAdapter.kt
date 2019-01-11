package com.hyeran.android.travely_manager.mypage

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hyeran.android.travely_manager.R
import com.hyeran.android.travely_manager.model.ReviewUserImgData
import java.sql.Timestamp


class ReviewListRVAdapter(val ctx : Context?, val dataList : ArrayList<ReviewUserImgData>) : RecyclerView.Adapter<ReviewListRVAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.item_review_list, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val requestOptions = RequestOptions()
                requestOptions.placeholder(R.drawable.mypage_bt_default)
                requestOptions.error(R.drawable.mypage_bt_default)

        Glide.with(holder!!.itemView.context)
                .setDefaultRequestOptions(requestOptions)
                .load(dataList[position].userImgUrl)
                .into(holder!!.profile)

        holder.name.text = dataList[position].userName
        var currentTime = System.currentTimeMillis()
        holder.hour.text = Timestamp(currentTime - dataList[position].createdAt).hours.toString()
        holder.start_num.text = dataList[position].like.toString()
        holder.content.text = dataList[position].content
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val profile : ImageView = itemView.findViewById(R.id.iv_profile_item_review_list) as ImageView
        val name : TextView = itemView.findViewById(R.id.tv_name_review_list) as TextView
        val hour : TextView = itemView.findViewById(R.id.tv_hour_review_list) as TextView
        val start_num : TextView = itemView.findViewById(R.id.tv_star_num_review_list) as TextView
        val content : TextView = itemView.findViewById(R.id.tv_content_review_list) as TextView
    }
}