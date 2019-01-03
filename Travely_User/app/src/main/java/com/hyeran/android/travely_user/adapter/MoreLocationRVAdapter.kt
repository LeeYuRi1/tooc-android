package com.hyeran.android.travely_user.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hyeran.android.travely_user.R
import android.util.Log
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.hyeran.android.travely_user.map.LocationListActivity
import com.hyeran.android.travely_user.model.region.SimpleStoreResponseData

class MoreLocationRVAdapter(val ctx : Context, val dataList: ArrayList<SimpleStoreResponseData>) : RecyclerView.Adapter<MoreLocationRVAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.item_more_location_map, parent, false)

        Toast.makeText(ctx, dataList.toString(), Toast.LENGTH_SHORT).show()

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = dataList[position].storeName
        Log.d("MoreLocationRVAdapter", "@@@@@@@@@@@@@"+dataList[position])
        holder.item_more_location_map.setOnClickListener {
            val intent : Intent = Intent()
            intent.putExtra("storeIdx", dataList[position].storeIdx)
            (ctx as LocationListActivity).finish()
        }
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.tv_name_item_more_location_map) as TextView
        val item_more_location_map : LinearLayout = itemView.findViewById(R.id.item_more_location_map) as LinearLayout
    }
}