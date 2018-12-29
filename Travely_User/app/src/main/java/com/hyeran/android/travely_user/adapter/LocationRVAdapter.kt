package com.hyeran.android.travely_user.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.data.LocationTempData
import com.hyeran.android.travely_user.data.MoreLocationTempData
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.map.MapMorePreviewFragment
import kotlinx.android.synthetic.main.item_location_map.view.*

class LocationRVAdapter(val ctx : Context, val dataList: ArrayList<LocationTempData>) : RecyclerView.Adapter<LocationRVAdapter.Holder>() {

    lateinit var moreLocationRVAdapter : MoreLocationRVAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.item_location_map, parent, false)

        view.iv_more_item_location_map.setOnClickListener {
            if(view.rv_more_item_location_map.visibility == View.VISIBLE) {
                view.rv_more_item_location_map.visibility = View.GONE
            }
            else {
                view.rv_more_item_location_map.visibility = View.VISIBLE
            }
        }

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = dataList[position].name
        holder.num.text = dataList[position].num.toString()

        var dataList : ArrayList<MoreLocationTempData> = ArrayList()

        if(position==0) {
        dataList.add(MoreLocationTempData("홍대 롭스 2호점"))
        dataList.add(MoreLocationTempData("동대문엽기떡볶이 홍대점"))
        dataList.add(MoreLocationTempData("김가네 홍대점"))
        }
        else if(position==1) {
            dataList.add(MoreLocationTempData("혜화 롭스 2호점"))
            dataList.add(MoreLocationTempData("동대문엽기떡볶이 혜화점"))
            dataList.add(MoreLocationTempData("김가네 혜화점"))
        }
        else if(position==2) {
            dataList.add(MoreLocationTempData("동역사 롭스 2호점"))
            dataList.add(MoreLocationTempData("동대문엽기떡볶이 동역사점"))
            dataList.add(MoreLocationTempData("김가네 동역사점"))
        }

        moreLocationRVAdapter = MoreLocationRVAdapter(ctx, dataList)
        holder.rv_more_item_location_map.adapter = moreLocationRVAdapter
        holder.rv_more_item_location_map.layoutManager = LinearLayoutManager(ctx)

    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.tv_name_item_location_map) as TextView
        val num : TextView = itemView.findViewById(R.id.tv_num_item_location_map) as TextView
        val rv_more_item_location_map = itemView.findViewById(R.id.rv_more_item_location_map) as RecyclerView
    }
}