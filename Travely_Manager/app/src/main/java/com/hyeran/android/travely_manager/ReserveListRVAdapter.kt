package com.hyeran.android.travely_manager

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import org.w3c.dom.Text
import android.support.v7.app.AppCompatActivity

class ReserveListRVAdapter(val ctx : Context?, val dataList : ArrayList<ReserveListTempData>) : RecyclerView.Adapter<ReserveListRVAdapter.r_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): r_Holder {
        // 뷰 인플레이트!!
        val r_view : View = LayoutInflater.from(ctx).inflate(R.layout.item_reserve_list, parent, false)

        return r_Holder(r_view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: r_Holder, position: Int) {
//        holder.r_profile.setImageResource(dataList[position].r_profile)
        holder.r_name.text = dataList[position].r_name
        holder.r_payment_status.text = dataList[position].r_payment_status
        holder.r_date.text = dataList[position].r_date
        holder.r_price.text = dataList[position].r_price.toString()
        holder.r_amount.text = dataList[position].r_amount.toString()
        holder.r_time.text = dataList[position].r_time

        holder.r_view.setOnClickListener {
            val manager = (ctx as AppCompatActivity).supportFragmentManager
            val transaction : FragmentTransaction = manager.beginTransaction()
            transaction.replace(R.id.frame_main, ReserveDetailFragment())
            transaction.commit()
        }
    }

    inner class r_Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
//        val r_profile : ImageView = itemView.findViewById(R.id.iv_profile_item_reserve_list) as ImageView
        val r_name : TextView = itemView.findViewById(R.id.tv_name_item_reserve_list) as TextView
        val r_payment_status : TextView = itemView.findViewById(R.id.tv_payment_status_item_reserve_list) as TextView
        val r_date : TextView = itemView.findViewById(R.id.tv_date_item_reserve_list) as TextView
        val r_price : TextView = itemView.findViewById(R.id.tv_price_item_reserve_list) as TextView
        val r_amount : TextView = itemView.findViewById(R.id.tv_amount_item_reserve_list) as TextView
        val r_time : TextView = itemView.findViewById(R.id.tv_time_item_reserve_list) as TextView

        val r_view : RelativeLayout = itemView.findViewById(R.id.item_reserve_list) as RelativeLayout
    }
}