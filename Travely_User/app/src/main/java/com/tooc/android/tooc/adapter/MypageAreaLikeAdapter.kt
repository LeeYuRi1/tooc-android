package com.tooc.android.tooc.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tooc.android.tooc.R
import com.tooc.android.tooc.model.mypage.FavoriteResponseData
import com.tooc.android.tooc.model.mypage.SimpleStoreResponseDtosData
import java.util.ArrayList

class MypageAreaLikeAdapter(val ctx: Context, val dataList: ArrayList<FavoriteResponseData>) : RecyclerView.Adapter<MypageAreaLikeAdapter.Holder>() {

    lateinit var mypageLikeAdapter: MypageLikeAdapter

    lateinit var favorite_dataList: ArrayList<SimpleStoreResponseDtosData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_area_like, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name_arealike.text = dataList[position].regionName
        holder.num_arealike.text = dataList[position].simpleStoreResponseDtos.size.toString()

        favorite_dataList = dataList[position].simpleStoreResponseDtos
        mypageLikeAdapter = MypageLikeAdapter(ctx, favorite_dataList)
        var position = mypageLikeAdapter.itemCount
        
        holder.rv_arealike.adapter = mypageLikeAdapter
        holder.rv_arealike.layoutManager = LinearLayoutManager(ctx)
        mypageLikeAdapter.notifyItemInserted(position)

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name_arealike: TextView = itemView.findViewById(R.id.tv_areaname_arealike) as TextView
        var num_arealike: TextView = itemView.findViewById(R.id.tv_num_arealike) as TextView
        var rv_arealike: RecyclerView = itemView.findViewById(R.id.rv_like_arealike)
    }
}