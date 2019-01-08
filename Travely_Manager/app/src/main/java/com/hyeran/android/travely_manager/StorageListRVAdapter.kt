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
import com.hyeran.android.travely_manager.model.StoreResponseDto
import org.w3c.dom.Text
import java.text.SimpleDateFormat

class StorageListRVAdapter(val ctx : Context?, val dataList : ArrayList<StoreResponseDto>) : RecyclerView.Adapter<StorageListRVAdapter.s_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): s_Holder {
        // 뷰 인플레이트!!
        val s_view : View = LayoutInflater.from(ctx).inflate(R.layout.item_storage_list, parent, false)
        return s_Holder(s_view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: s_Holder, position: Int) {
        var dateFormat = SimpleDateFormat("yyyy.MM.dd")
        var timeFormat = SimpleDateFormat("HH:mm")
        var paymentStatus: String
        var date : String = dateFormat.format(dataList[position].startTime)
        var time : String = timeFormat.format(dataList[position].startTime)

        //TODO 의심리스트!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        var amount = 0
        for(i in 0..dataList[position].bagDtos.size){
            amount+=dataList[position].bagDtos[i].bagCount.toInt()
        }
        if(dataList[position].progressType=="ING") {
            paymentStatus= "결제진행중"
        }
        else{
            paymentStatus = "결제완료"

        }

        holder.s_name.text = dataList[position].userName
        holder.s_payment_status.text = paymentStatus
        holder.s_date.text = date
        holder.s_price.text = dataList[position].price.toString()
        holder.s_amount.text = amount.toString()
        holder.s_time.text = time

        holder.s_view.setOnClickListener {
            val manager = (ctx as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.replace(R.id.frame_main, ReserveDetailFragment())
            transaction.commit()
        }
    }

    inner class s_Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val s_name : TextView = itemView.findViewById(R.id.tv_name_item_storage_list) as TextView
        val s_payment_status : TextView = itemView.findViewById(R.id.tv_payment_status_item_storage_list) as TextView
        val s_date : TextView = itemView.findViewById(R.id.tv_date_item_storage_list) as TextView
        val s_price : TextView = itemView.findViewById(R.id.tv_price_item_storage_list) as TextView
        val s_amount : TextView = itemView.findViewById(R.id.tv_amount_item_storage_list) as TextView
        val s_time : TextView = itemView.findViewById(R.id.tv_time_item_storage_list) as TextView
        val s_view : RelativeLayout = itemView.findViewById(R.id.item_storage_list) as RelativeLayout
    }
}