package com.hyeran.android.travely_manager

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.BundleCompat
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
import com.hyeran.android.travely_manager.model.ReserveResponseDto
import com.hyeran.android.travely_manager.model.StoreResponseDto
import java.text.SimpleDateFormat

class ReserveListRVAdapter(val ctx : Context?, val dataList : ArrayList<ReserveResponseDto>) : RecyclerView.Adapter<ReserveListRVAdapter.r_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): r_Holder {
        // 뷰 인플레이트!!
        val r_view : View = LayoutInflater.from(ctx).inflate(R.layout.item_reserve_list, parent, false)

        return r_Holder(r_view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: r_Holder, position: Int) {
        var dateFormat = SimpleDateFormat("yyyy.MM.dd")
        var timeFormat = SimpleDateFormat("HH:mm")
        var paymentStatus: String
        var date : String = dateFormat.format(dataList[position].startTime)
        var time : String = timeFormat.format(dataList[position].startTime)
        //TODO 의심리스트!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        var amount : Int =0
        for(i in 0..dataList[position].bagDtos.size-1){
            amount+=dataList[position].bagDtos[i].bagCount.toInt()
        }
        if(dataList[position].progressType=="ING") {
            paymentStatus= "결제진행중"
        }
        else{
            paymentStatus = "결제완료"
        }

        holder.r_name.text = dataList[position].userName
        holder.r_payment_status.text = paymentStatus
        holder.r_date.text = date
        holder.r_price.text = dataList[position].price.toString()
        holder.r_amount.text = amount.toString()
        holder.r_time.text = time

        holder.r_view.setOnClickListener {
            val manager = (ctx as AppCompatActivity).supportFragmentManager
            val transaction : FragmentTransaction = manager.beginTransaction()
            var args =Bundle()
            args.putString("reserveIdx",dataList[position].reserveIdx.toString())
            var fragment = ReserveDetailFragment()
            fragment.arguments = args
            transaction.replace(R.id.frame_main, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    inner class r_Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val r_name : TextView = itemView.findViewById(R.id.tv_name_item_reserve_list) as TextView
        val r_payment_status : TextView = itemView.findViewById(R.id.tv_payment_status_item_reserve_list) as TextView
        val r_date : TextView = itemView.findViewById(R.id.tv_date_item_reserve_list) as TextView
        val r_price : TextView = itemView.findViewById(R.id.tv_price_item_reserve_list) as TextView
        val r_amount : TextView = itemView.findViewById(R.id.tv_amount_item_reserve_list) as TextView
        val r_time : TextView = itemView.findViewById(R.id.tv_time_item_reserve_list) as TextView
        val r_view : RelativeLayout = itemView.findViewById(R.id.rv_reserve_list_btn) as RelativeLayout
    }
}