package com.tooc.android.tooc.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.tooc.android.tooc.model.mypage.ReviewLookupData
import java.text.SimpleDateFormat
import com.tooc.android.tooc.R
import com.tooc.android.tooc.network.ApplicationController
import com.tooc.android.tooc.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MypageMyReviewAdapter(val ctx: Context, val dataList : ArrayList<ReviewLookupData>)
    : RecyclerView.Adapter<MypageMyReviewAdapter.Holder>() {

    var reviewnum = 0
    lateinit var delete : Any

    lateinit var networkService : NetworkService

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.item_myreview , parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {


        networkService = ApplicationController.instance.networkService

        Glide.with(holder!!.itemView.context)
                .load(dataList[position].storeImgUrl)
                .into(holder!!.store_image)

        holder.store_name.text = dataList[position].storeName
        holder.store_area.text = dataList[position].address

        reviewnum = dataList[position].reviewIdx

//        holder.review_year.text = dataList[position].year
//        holder.review_month.text = dataList[position].month
//        holder.review_day.text = dataList[position].day

        var createDate : Long = dataList[position].createAt
        var simpleDate = SimpleDateFormat("yyyy.MM.dd")
        holder.review_ymd.text = simpleDate.format(createDate)
        holder.review_content.text = dataList[position].content
        holder.review_rating.rating = dataList[position].liked.toFloat()

        holder.review_delete.setOnClickListener {
            deleteReviewResponse()
            delete = this.dataList.get(position)
        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val store_image : ImageView = itemView.findViewById(R.id.iv_storeimage_myreview) as ImageView
        val store_name: TextView = itemView.findViewById(R.id.tv_name_myreview) as TextView
        val store_area: TextView = itemView.findViewById(R.id.tv_area_myreview) as TextView
        /*
        var review_year : TextView = itemView.findViewById(R.id.tv_year_myreview) as TextView
        var review_month : TextView = itemView.findViewById(R.id.tv_month_myreview) as TextView
        var review_day : TextView = itemView.findViewById(R.id.tv_day_myreview) as TextView
        */
        var review_ymd : TextView = itemView.findViewById(R.id.tv_ymd_myreview) as TextView
        val review_content : TextView = itemView.findViewById(R.id.tv_content_myreview) as TextView
        var review_rating : RatingBar = itemView.findViewById(R.id.ratingBar_myreview) as RatingBar
        var review_delete : TextView = itemView.findViewById(R.id.btn_delete_myreview) as TextView
    }

    fun deleteReviewResponse() {
        var jwt : String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")

        var deleteReviewResponse = networkService.deleteReviewResponse(jwt,reviewnum)   //riviewIdx 가져오기

        deleteReviewResponse!!.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
            }
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            this@MypageMyReviewAdapter.dataList.remove(delete)
                            this@MypageMyReviewAdapter.notifyDataSetChanged()
                            Toast.makeText(ctx, "리뷰 삭제 성공", Toast.LENGTH_SHORT).show()
                        }
                        400 -> {
                            Toast.makeText(ctx, "잘못된 접근", Toast.LENGTH_SHORT).show()
                        }
                        500 -> {
                            Toast.makeText(ctx, "서버에러", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(ctx, "error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }

}