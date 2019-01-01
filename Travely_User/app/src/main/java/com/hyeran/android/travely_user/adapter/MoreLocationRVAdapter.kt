package com.hyeran.android.travely_user.adapter

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hyeran.android.travely_user.data.MoreLocationTempData
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.map.MapMorePreviewFragment
import android.R.attr.fragment
import android.app.Activity
import android.util.Log
import android.widget.RelativeLayout
import android.widget.Toast
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.map.TempActivity
import com.hyeran.android.travely_user.model.SimpleStoreResponseData
import org.jetbrains.anko.startActivity


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
            (ctx as TempActivity).finish()
        }
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.tv_name_item_more_location_map) as TextView
        val item_more_location_map : RelativeLayout = itemView.findViewById(R.id.item_more_location_map) as RelativeLayout
    }
}