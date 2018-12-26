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



class ReserveListRVAdapter(val ctx : Context?, val dataList : ArrayList<ReserveListTempData>) : RecyclerView.Adapter<ReserveListRVAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.item_reserve_list, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.profile.setImageResource(dataList[position].profile)
        holder.name.text = dataList[position].name
        holder.payment_status.text = dataList[position].payment_status
        holder.price.text = dataList[position].price.toString()
        holder.amount.text = dataList[position].amount.toString()
        holder.am_pm.text = dataList[position].am_pm
        holder.hour.text = dataList[position].hour.toString()
        holder.minute.text = dataList[position].minute.toString()

        holder.view.setOnClickListener {
            val manager = (ctx as AppCompatActivity).supportFragmentManager
            val transaction : FragmentTransaction = manager.beginTransaction()
            transaction.replace(R.id.frame_main, ReserveDetailFragment())
            transaction.commit()
        }
    }



    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val profile : ImageView = itemView.findViewById(R.id.iv_profile_item_reserve_list) as ImageView
        val name : TextView = itemView.findViewById(R.id.tv_name_item_reserve_list) as TextView
        val payment_status : TextView = itemView.findViewById(R.id.tv_payment_status_item_reserve_list) as TextView
        val price : TextView = itemView.findViewById(R.id.tv_price_item_reserve_list) as TextView
        val amount : TextView = itemView.findViewById(R.id.tv_amount_item_reserve_list) as TextView
        val am_pm : TextView = itemView.findViewById(R.id.tv_am_pm_item_reserve_list) as TextView
        val hour : TextView = itemView.findViewById(R.id.tv_hour_item_reserve_list) as TextView
        val minute : TextView = itemView.findViewById(R.id.tv_minute_item_reserve_list) as TextView

        val view : RelativeLayout = itemView.findViewById(R.id.item_reserve_list) as RelativeLayout
    }
}