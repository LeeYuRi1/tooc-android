package com.hyeran.android.travely_user.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.R.id.rv_like_like
import com.hyeran.android.travely_user.data.MypageAreaLikeData
import com.hyeran.android.travely_user.data.MypageLikeData
import java.util.ArrayList

class MypageAreaLikeAdapter(val ctx: Context, val dataList: ArrayList<MypageAreaLikeData>) : RecyclerView.Adapter<MypageAreaLikeAdapter.Holder>() {

    lateinit var mypageLikeAdapter: MypageLikeAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_area_like, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name_arealike.text = dataList[position].name
        holder.num_arealike.text = dataList[position].num.toString()

        var dataList: ArrayList<MypageLikeData> = ArrayList()

//        if(position == 0){
//            dataList.add(MypageLikeData("프로마치", "성북구 안암동 123번지", "10:00 - 23:00", true))
//            dataList.add(MypageLikeData("프로마치", "성북구 안암동 123번지", "10:00 - 23:00", false))
//            dataList.add(MypageLikeData("프로마치", "성북구 안암동 123번지", "10:00 - 23:00", true))
//        }
//        else if(position == 1){
//            dataList.add(MypageLikeData("프로마치2", "성북구 안암동 123번지", "10:00 - 23:00", true))
//            dataList.add(MypageLikeData("프로마치2", "성북구 안암동 123번지", "10:00 - 23:00", false))
//            dataList.add(MypageLikeData("프로마치2", "성북구 안암동 123번지", "10:00 - 23:00", true))
//        }
//        else if(position == 2){
//            dataList.add(MypageLikeData("프로마치3", "성북구 안암동 123번지", "10:00 - 23:00", true))
//            dataList.add(MypageLikeData("프로마치3", "성북구 안암동 123번지", "10:00 - 23:00", false))
//            dataList.add(MypageLikeData("프로마치3", "성북구 안암동 123번지", "10:00 - 23:00", true))
//        }

        mypageLikeAdapter = MypageLikeAdapter(ctx, dataList)
        holder.rv_arealike.adapter = mypageLikeAdapter
        holder.rv_arealike.layoutManager = LinearLayoutManager(ctx)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name_arealike: TextView = itemView.findViewById(R.id.tv_areaname_arealike) as TextView
        var num_arealike: TextView = itemView.findViewById(R.id.tv_num_arealike) as TextView
        var rv_arealike: RecyclerView = itemView.findViewById(R.id.rv_like_arealike)
    }
}