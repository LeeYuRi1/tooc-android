package com.tooc.android.tooc.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.LinearLayout
import com.tooc.android.tooc.R
import com.tooc.android.tooc.map.LocationListActivity
import com.tooc.android.tooc.model.region.SimpleStoreResponseData

class MoreLocationRVAdapter(val ctx: Context, val dataList: ArrayList<SimpleStoreResponseData>) : RecyclerView.Adapter<MoreLocationRVAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_more_location_map, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = dataList[position].storeName
        dataList[position].storeIdx

        holder.item_more_location_map.setOnClickListener {
            (ctx as LocationListActivity).getStoreIdx(dataList[position].storeIdx)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name_item_more_location_map) as TextView
        val item_more_location_map: LinearLayout = itemView.findViewById(R.id.item_more_location_map) as LinearLayout
    }

}