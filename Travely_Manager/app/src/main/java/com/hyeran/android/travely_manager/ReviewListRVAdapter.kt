package com.hyeran.android.travely_manager

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.support.v7.app.AppCompatActivity
import org.w3c.dom.Text


class ReviewListRVAdapter(val ctx : Context?, val dataList : ArrayList<ReviewListTempData>) : RecyclerView.Adapter<ReviewListRVAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.item_review_list, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.profile.setImageResource(dataList[position].profile)
        holder.name.text = dataList[position].name
        holder.hour.text = dataList[position].hour.toString()
        holder.start_num.text = dataList[position].start_num.toString()
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