package com.hyeran.android.travely_user.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.data.LocationTempData
import com.hyeran.android.travely_user.data.MoreLocationTempData
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.map.MapMorePreviewFragment
import com.hyeran.android.travely_user.model.RegionResponseData
import com.hyeran.android.travely_user.model.SimpleStoreResponseData
import kotlinx.android.synthetic.main.item_location_map.view.*

class LocationRVAdapter(val ctx: Context, val dataList: ArrayList<RegionResponseData>) : RecyclerView.Adapter<LocationRVAdapter.Holder>() {

    lateinit var moreLocationRVAdapter: MoreLocationRVAdapter
//
//    val more_dataList: ArrayList<SimpleStoreResponseData> by lazy {
//        ArrayList<SimpleStoreResponseData>()
//    }

    lateinit var more_dataList : ArrayList<SimpleStoreResponseData>
    lateinit var moreLocationRVAdapterList : List<MoreLocationRVAdapter>

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_location_map, parent, false)

//        view.iv_more_item_location_map.setOnClickListener {
//            if (view.rv_more_item_location_map.visibility == View.VISIBLE) {
//                view.rv_more_item_location_map.visibility = View.GONE
//            } else {
//                view.rv_more_item_location_map.visibility = View.VISIBLE
//            }
//        }

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = dataList[position].regionName
        holder.num.text = dataList[position].simpleStoreResponseDtos.size.toString()

        var more_dataList_by_area: ArrayList<SimpleStoreResponseData> = dataList[position].simpleStoreResponseDtos

//
        Log.d("LocationRVAdapter", "@@@@@@@@@@@@@"+dataList[position])


        more_dataList = dataList[position].simpleStoreResponseDtos
        //moreLocationRVAdapterList
        //moreLocationRVAdapterList!!.indexOf(MoreLocationRVAdapter(ctx, more_dataList))
        moreLocationRVAdapter = MoreLocationRVAdapter(ctx, more_dataList)

        Log.d("LocationRVAdapter", "@@@@@@@@@@@@@"+dataList[position])
//
        val position = moreLocationRVAdapter.itemCount

//        moreLocationRVAdapter.dataList.clear()
        Log.v("Woo 929",more_dataList_by_area.toString())
//        moreLocationRVAdapter.dataList.addAll(more_dataList_by_area)
        Log.v("Woo 914",moreLocationRVAdapter.dataList.size.toString())
//        moreLocationRVAdapter.dataList.add(more_dataList)
//
        holder.rv_more_item_location_map.adapter = moreLocationRVAdapter
        holder.rv_more_item_location_map.layoutManager = LinearLayoutManager(ctx)
        moreLocationRVAdapter.notifyItemInserted(position)

        holder.iv_more_item_location_map.setOnClickListener {
            if (holder.rv_more_item_location_map.visibility == View.VISIBLE) {
                holder.rv_more_item_location_map.visibility = View.GONE
            } else {
                holder.rv_more_item_location_map.visibility = View.VISIBLE
            }
        }

//        for(i in 0..dataList.size-1) {
//
//            var more_dataList_by_area: ArrayList<SimpleStoreResponseData> = dataList[i].simpleStoreResponseDtos
//
//            moreLocationRVAdapter = MoreLocationRVAdapter(ctx, more_dataList)
//            holder.rv_more_item_location_map.adapter = moreLocationRVAdapter
//            holder.rv_more_item_location_map.layoutManager = LinearLayoutManager(ctx)
//
//
//            val position = moreLocationRVAdapter.itemCount
//            moreLocationRVAdapter.dataList.
//            moreLocationRVAdapter.dataList.addAll(more_dataList_by_area)
//            moreLocationRVAdapter.notifyItemInserted(position)
//
//        }
//
//        if(position==0) {
//        dataList.add(MoreLocationTempData("홍대 롭스 2호점"))
//        dataList.add(MoreLocationTempData("동대문엽기떡볶이 홍대점"))
//        dataList.add(MoreLocationTempData("김가네 홍대점"))
//        }
//        else if(position==1) {
//            dataList.add(MoreLocationTempData("혜화 롭스 2호점"))
//            dataList.add(MoreLocationTempData("동대문엽기떡볶이 혜화점"))
//            dataList.add(MoreLocationTempData("김가네 혜화점"))
//        }
//        else if(position==2) {
//            dataList.add(MoreLocationTempData("동역사 롭스 2호점"))
//            dataList.add(MoreLocationTempData("동대문엽기떡볶이 동역사점"))
//            dataList.add(MoreLocationTempData("김가네 동역사점"))
//        }


//        holder.item_btn.setOnClickListener {
//            ctx.startActivity<MapMoreActivity>()
//        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name_item_location_map) as TextView
        val num: TextView = itemView.findViewById(R.id.tv_num_item_location_map) as TextView
        val rv_more_item_location_map = itemView.findViewById(R.id.rv_more_item_location_map) as RecyclerView
        val item_location_map = itemView.findViewById(R.id.item_location_map) as LinearLayout
        val iv_more_item_location_map = itemView.findViewById(R.id.iv_more_item_location_map) as ImageView
    }
}