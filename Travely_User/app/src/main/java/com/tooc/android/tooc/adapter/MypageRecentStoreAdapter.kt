package com.tooc.android.tooc.adapter

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.tooc.android.tooc.dialog.WriteReviewDialog
import com.tooc.android.tooc.join.RecentstoreDetailFragment
import com.tooc.android.tooc.model.store.StoreResponseData
import com.tooc.android.tooc.model.StoreInfoResponseData
import com.tooc.android.tooc.network.ApplicationController
import com.tooc.android.tooc.network.NetworkService
import com.tooc.android.tooc.MainActivity
import com.tooc.android.tooc.R
import com.tooc.android.tooc.reserve.ReserveFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.util.ArrayList


class MypageRecentStoreAdapter(val ctx: Context, val dataList: ArrayList<StoreInfoResponseData>) : RecyclerView.Adapter<MypageRecentStoreAdapter.Holder>() {

    lateinit var networkService: NetworkService

    var reviewStoreIdx = 0

    var storeIdx : Int=0
    var isAvailable = true

    private fun init() {
        networkService = ApplicationController.instance.networkService
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_recentstore, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        init()
        reviewStoreIdx = dataList[position].storeIdx

        Glide.with(holder!!.itemView.context)
                .load(dataList[position].storeImage)
                .into(holder!!.recent_image)
        holder.recent_name.text = dataList[position].storeName
        holder.recent_addr.text = dataList[position].address

        //리뷰 작성
        holder.reviewwrite.setOnClickListener {
            WriteReviewDialog(ctx,dataList[position].storeIdx).show()
        }

        //TODO 최근보관한 곳 누를 시 예약현황으로 가야하는 곳 구현 미완성!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        holder.recent_detail.setOnClickListener {
            Log.d("TAGGGGGG","reserveIdx = "+dataList[position].reserveIdx.toString()+"    storeIdx = "+dataList[position].storeIdx)

            var args =Bundle()
            var fragment = RecentstoreDetailFragment()
            args.putInt("reserveIdx",dataList[position].reserveIdx)
            fragment.arguments = args
            (ctx as MainActivity).replaceFragmentBackStack(fragment)
        }

        holder.reserve_btn.setOnClickListener {
            storeIdx = dataList[position].storeIdx

            getStoreReserve()
        }

        var recentStartTime: Long = dataList[position].openTime

        if (Timestamp(recentStartTime).hours.toString().trim().length == 1) {
            holder.recent_start_hour.text = "0" + Timestamp(recentStartTime).hours.toString().trim()
        } else {
            holder.recent_start_hour.text = Timestamp(recentStartTime).hours.toString().trim()
        }
        if (Timestamp(recentStartTime).minutes.toString().trim().length == 1) {
            holder.recent_start_minute.text = "0" + Timestamp(recentStartTime).minutes.toString().trim()
        } else {
            holder.recent_start_minute.text = Timestamp(recentStartTime).minutes.toString().trim()
        }

        var recentEndTime: Long = dataList[position].closeTime
        if (Timestamp(recentEndTime).hours.toString().trim().length == 1) {
            holder.recent_end_hour.text = "0" + Timestamp(recentEndTime).hours.toString().trim()
        } else {
            holder.recent_end_hour.text = Timestamp(recentEndTime).hours.toString().trim()
        }
        if (Timestamp(recentEndTime).minutes.toString().trim().length == 1) {
            holder.recent_end_minute.text = "0" + Timestamp(recentEndTime).minutes.toString().trim()
        } else {
            holder.recent_end_minute.text = Timestamp(recentEndTime).minutes.toString().trim()
        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recent_image: ImageView = itemView.findViewById(R.id.iv_storeimage_recentstore) as ImageView
        var recent_name: TextView = itemView.findViewById(R.id.tv_name_recentstore) as TextView
        var recent_addr: TextView = itemView.findViewById(R.id.tv_addr_recentstore) as TextView
        var recent_start_hour: TextView = itemView.findViewById(R.id.tv_starttime_hour_recentstore) as TextView
        var recent_start_minute: TextView = itemView.findViewById(R.id.tv_starttime_minute_recentstore) as TextView
        var recent_end_hour: TextView = itemView.findViewById(R.id.tv_endtime_hour_recentstore) as TextView
        var recent_end_minute: TextView = itemView.findViewById(R.id.tv_endtime_minute_recentstore) as TextView
        var reviewwrite: Button = itemView.findViewById(R.id.btn_reviewwrite_recentstore) as Button
        var recent_detail: ConstraintLayout = itemView.findViewById(R.id.constraint_recentstore) as ConstraintLayout
        var reserve_btn: Button = itemView.findViewById(R.id.button6) as Button
    }

    private fun getStoreReserve() {
        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        val getStoreResponse = networkService.getStoreResponse(jwt, storeIdx)
        getStoreResponse.enqueue(object : Callback<StoreResponseData> {
            override fun onFailure(call: Call<StoreResponseData>, t: Throwable) {
            }

            override fun onResponse(call: Call<StoreResponseData>, response: Response<StoreResponseData>) {
                response.let {
                    when (it.code()) {
                        200 -> {
                            isAvailable = response.body()!!.available != -1
                            var reserve = SharedPreferencesController.instance!!.getPrefBooleanData("is_reserve")
                            if(response.body()!!.currentBag<response.body()!!.limit) {
                                if (isAvailable == false) {
                                    Toast.makeText(ctx, "해당 상가는 예약 가능한 상태가 아닙니다.", Toast.LENGTH_LONG).show()
                                } else {
                                    if (reserve == false) {
                                        var args = Bundle()
                                        var fragment: Fragment = ReserveFragment()
                                        args.putInt("storeIdx", storeIdx)
                                        fragment.arguments = args
                                        (ctx as MainActivity).selectedTabChangeColor(1)
                                        (ctx).replaceFragment(fragment)
                                    } else {
                                        Toast.makeText(ctx, "이미 예약 내역이 존재합니다.", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }else{
                                Toast.makeText(ctx, "해당 상가는 예약 가능한 상태가 아닙니다.", Toast.LENGTH_LONG).show()
                            }
                        }
                        500 -> {
                        }
                        else -> {
                        }
                    }
                }
            }
        })
    }

}
