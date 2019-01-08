package com.hyeran.android.travely_manager

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import org.w3c.dom.Text

class StorageListRVAdapter(val ctx : Context?, val dataList : ArrayList<StorageListTempData>) : RecyclerView.Adapter<StorageListRVAdapter.s_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): s_Holder {
        // 뷰 인플레이트!!
        val s_view : View = LayoutInflater.from(ctx).inflate(R.layout.item_storage_list, parent, false)

        return s_Holder(s_view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: s_Holder, position: Int) {
//        holder.s_profile.setImageResource(dataList[position].s_profile)
        holder.s_name.text = dataList[position].s_name
        holder.s_payment_status.text = dataList[position].s_payment_status
        holder.s_date.text = dataList[position].s_date
        holder.s_price.text = dataList[position].s_price.toString()
        holder.s_amount.text = dataList[position].s_amount.toString()
        holder.s_time.text = dataList[position].s_time

        holder.s_view.setOnClickListener {
            val manager = (ctx as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.replace(R.id.frame_main, ReserveDetailFragment())
            transaction.commit()
        }
    }

    inner class s_Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
//        val s_profile : ImageView = itemView.findViewById(R.id.iv_profile_item_storage_list) as ImageView
        val s_name : TextView = itemView.findViewById(R.id.tv_name_item_storage_list) as TextView
        val s_payment_status : TextView = itemView.findViewById(R.id.tv_payment_status_item_storage_list) as TextView
        val s_date : TextView = itemView.findViewById(R.id.tv_date_item_storage_list) as TextView
        val s_price : TextView = itemView.findViewById(R.id.tv_price_item_storage_list) as TextView
        val s_amount : TextView = itemView.findViewById(R.id.tv_amount_item_storage_list) as TextView
        val s_time : TextView = itemView.findViewById(R.id.tv_time_item_storage_list) as TextView

        val s_view : RelativeLayout = itemView.findViewById(R.id.item_storage_list) as RelativeLayout
    }
}