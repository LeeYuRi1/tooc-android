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
import com.hyeran.android.travely_user.model.store.StoreResponseData
import com.hyeran.android.travely_user.network.NetworkService
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoreLocationRVAdapter(val ctx : Context, val dataList: ArrayList<SimpleStoreResponseData>) : RecyclerView.Adapter<MoreLocationRVAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.item_more_location_map, parent, false)

        Toast.makeText(ctx, dataList.toString(), Toast.LENGTH_SHORT).show()

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = dataList[position].storeName
        dataList[position].storeIdx

        Log.d("MoreLocationRVAdapter", "@@@@@@@@@@@@@"+dataList[position])
        holder.item_more_location_map.setOnClickListener {

            Log.d("@storeIdx통신@", "item이 클릭되었습니다.")
            Log.d("@storeIdx통신@", "해당 storeIdx는? "+dataList[position].storeIdx)
            (ctx as LocationListActivity).getStoreIdx(dataList[position].storeIdx)
//            ctx.finish()
        }
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.tv_name_item_more_location_map) as TextView
        val item_more_location_map : LinearLayout = itemView.findViewById(R.id.item_more_location_map) as LinearLayout
    }


}