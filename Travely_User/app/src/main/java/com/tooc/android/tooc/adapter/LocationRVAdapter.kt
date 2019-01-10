package com.tooc.android.tooc.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.hyeran.android.tooc.R
import com.tooc.android.tooc.model.region.RegionResponseData
import com.tooc.android.tooc.model.region.SimpleStoreResponseData

class LocationRVAdapter(val ctx: Context, val dataList: ArrayList<RegionResponseData>) : RecyclerView.Adapter<LocationRVAdapter.Holder>() {

    lateinit var moreLocationRVAdapter: MoreLocationRVAdapter

    lateinit var more_dataList: ArrayList<SimpleStoreResponseData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_location_map, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = dataList[position].regionName
        holder.num.text = dataList[position].simpleStoreResponseDtos.size.toString()

        more_dataList = dataList[position].simpleStoreResponseDtos
        moreLocationRVAdapter = MoreLocationRVAdapter(ctx, more_dataList)
        val position = moreLocationRVAdapter.itemCount

        holder.rv_more_item_location_map.adapter = moreLocationRVAdapter
        holder.rv_more_item_location_map.layoutManager = LinearLayoutManager(ctx)
        moreLocationRVAdapter.notifyItemInserted(position)

        holder.iv_more_item_location_map.setOnClickListener {
            if (holder.rv_more_item_location_map.visibility == View.VISIBLE) {
                holder.iv_arrow_item_location_map.setImageResource(R.drawable.ic_arrow_down)
                holder.rv_more_item_location_map.visibility = View.GONE
            } else {
                holder.iv_arrow_item_location_map.setImageResource(R.drawable.ic_arrow_up)
                holder.rv_more_item_location_map.visibility = View.VISIBLE
            }
        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name_item_location_map) as TextView
        val num: TextView = itemView.findViewById(R.id.tv_num_item_location_map) as TextView
        val rv_more_item_location_map = itemView.findViewById(R.id.rv_more_item_location_map) as RecyclerView
        val iv_more_item_location_map = itemView.findViewById(R.id.iv_more_item_location_map) as LinearLayout
        val iv_arrow_item_location_map = itemView.findViewById(R.id.iv_arrow_item_location_map) as ImageView
    }
}