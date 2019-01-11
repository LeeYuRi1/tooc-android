package com.tooc.android.tooc.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.tooc.android.tooc.MainActivity
import com.tooc.android.tooc.R
import com.tooc.android.tooc.R.id.btn_map_more_act_favorite
import com.tooc.android.tooc.map.MapMoreActivity
import com.tooc.android.tooc.model.mypage.SimpleStoreResponseDtosData
import com.tooc.android.tooc.model.mypage.StoreFavoriteResponseData
import com.tooc.android.tooc.model.store.StoreResponseData
import com.tooc.android.tooc.mypage.LikeFragment
import com.tooc.android.tooc.mypage.MyreviewFragment
import com.tooc.android.tooc.network.ApplicationController
import com.tooc.android.tooc.network.NetworkService
import com.tooc.android.tooc.reserve.ReserveFragment
import kotlinx.android.synthetic.main.activity_map_more.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.toast
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.util.ArrayList

class MypageLikeAdapter(val ctx: Context, val dataList: ArrayList<SimpleStoreResponseDtosData>) : RecyclerView.Adapter<MypageLikeAdapter.Holder>() {
    lateinit var networkService: NetworkService
    var storeIdx: Int = 0
    var likeStoreIdx: Int = 0
    var isAvailable = true
    var likeFragment = LikeFragment()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_like, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        networkService = ApplicationController.instance.networkService

        dataList[position].storeIdx
        likeFragment.getLikeStoreIdx(dataList[position].storeIdx)

        Glide.with(holder!!.itemView.context)
                .load(dataList[position].storeImgUrl)
                .into(holder!!.like_image)
        holder.like_name.text = dataList[position].storeName
        holder.like_addr.text = dataList[position].address
        holder.like_rating.rating = dataList[position].grade


        var likeStartTime: Long = dataList[position].openTime
        if (Timestamp(likeStartTime).hours.toString().trim().length == 1) {
            holder.like_start_hour.text = "0" + Timestamp(likeStartTime).hours.toString().trim()
        } else {
            holder.like_start_hour.text = Timestamp(likeStartTime).hours.toString().trim()
        }
        if (Timestamp(likeStartTime).minutes.toString().trim().length == 1) {
            holder.like_start_minute.text = "0" + Timestamp(likeStartTime).minutes.toString().trim()
        } else {
            holder.like_start_minute.text = Timestamp(likeStartTime).minutes.toString().trim()
        }

        var likeEndTime: Long = dataList[position].closeTime
        if (Timestamp(likeEndTime).hours.toString().trim().length == 1) {
            holder.like_end_hour.text = "0" + Timestamp(likeEndTime).hours.toString().trim()
        } else {
            holder.like_end_hour.text = Timestamp(likeEndTime).hours.toString().trim()
        }
        if (Timestamp(likeEndTime).minutes.toString().trim().length == 1) {
            holder.like_end_minute.text = "0" + Timestamp(likeEndTime).minutes.toString().trim()
        } else {
            holder.like_end_minute.text = Timestamp(likeEndTime).minutes.toString().trim()
        }

        holder.like_heart.isSelected = true

        likeStoreIdx = dataList[position].storeIdx

        //즐겨찾기 취소
        holder.like_heart.setOnClickListener {
            holder.like_heart.isSelected = false
            putFavoriteResponse()
        }


        holder.like_reserve_btn.setOnClickListener {
            storeIdx = dataList[position].storeIdx
            getStoreReserve()
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var like_image: ImageView = itemView.findViewById(R.id.iv_storeimage_like) as ImageView
        var like_name: TextView = itemView.findViewById(R.id.tv_name_like) as TextView
        var like_addr: TextView = itemView.findViewById(R.id.tv_addr_like) as TextView
        var like_heart: ImageView = itemView.findViewById(R.id.iv_heart_like) as ImageView
        var like_rating: RatingBar = itemView.findViewById(R.id.ratingBar_like) as RatingBar

        var like_start_hour: TextView = itemView.findViewById(R.id.tv_starttime_hour_like) as TextView
        var like_start_minute: TextView = itemView.findViewById(R.id.tv_starttime_minute_like) as TextView
        var like_end_hour: TextView = itemView.findViewById(R.id.tv_endtime_hour_like) as TextView
        var like_end_minute: TextView = itemView.findViewById(R.id.tv_endtime_minute_like) as TextView
        var like_reserve_btn: Button = itemView.findViewById(R.id.button6) as Button
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
                            if (response.body()!!.currentBag < response.body()!!.limit) {
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
                            } else {
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

    private fun putFavoriteResponse() {
        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")

        val putStoreFavoriteResponse = networkService.putStoreFavoriteResponse(jwt, likeStoreIdx)

        putStoreFavoriteResponse!!.enqueue(object : Callback<StoreFavoriteResponseData> {
            override fun onFailure(call: Call<StoreFavoriteResponseData>, t: Throwable) {
            }

            override fun onResponse(call: Call<StoreFavoriteResponseData>, response: Response<StoreFavoriteResponseData>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            (ctx as MainActivity).replaceFragment(LikeFragment())
                        }
                        400 -> {
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