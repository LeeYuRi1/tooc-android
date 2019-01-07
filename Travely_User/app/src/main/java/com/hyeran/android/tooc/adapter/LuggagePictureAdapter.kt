package com.hyeran.android.tooc.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hyeran.android.tooc.data.LuggagePictureData
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.model.reservation.bagImgDtos


class LuggagePictureAdapter(val ctx: Context, val dataList: ArrayList<bagImgDtos>) : RecyclerView.Adapter<LuggagePictureAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_luggage_picture, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.bind(dataList[position],ctx)

        if (dataList != null) {
            Glide.with(ctx)
                    .load(dataList[position].bagImgUrl)   //teset 다른걸로 바꿔놨음!!!
                    .into(holder.picture)
        } else {
            Glide.with(ctx)
                    .load("https://s3.ap-northeast-2.amazonaws.com/travely-project/KakaoTalk_20181231_201927117.jpg")
                    .into(holder.picture)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picture: ImageView = itemView.findViewById(R.id.iv_luggage_picture) as ImageView

//        fun bind(pictureImage: LuggagePictureData, context: Context) {
//            if (pictureImage.picture != "") {
//                val resouceId = context.resources.getIdentifier(pictureImage.picture, "drawble", context.packageName)
//                picture.setImageResource(resouceId)
//            } else {
//                picture.setImageResource(R.drawable.ic_launcher_background)
//            }
//        }
    }
}