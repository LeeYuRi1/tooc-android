package com.hyeran.android.travely_user

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide


class LuggagePictureAdapter(val ctx: Context, val dataPicture: ArrayList<LuggagePictureData>) : RecyclerView.Adapter<LuggagePictureAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_luggage_picture, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataPicture.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(dataPicture[position],ctx)

        if (dataPicture != null) {
//            Glide.with(ctx)
//                    .load(dataPicture!![position])
//                    .into(holder.picture)
            Glide.with(ctx)
                    .load(R.drawable.teset)
                    .into(holder.picture)
        } else {
            Glide.with(ctx)
                    .load(R.drawable.teset)
                    .into(holder.picture)

//            holder.picture.setImageResource(R.drawable.teset)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picture: ImageView = itemView.findViewById(R.id.iv_luggage_picture) as ImageView

        fun bind(pictureImage: LuggagePictureData, context: Context) {
            if (pictureImage.picture != "") {
                val resouceId = context.resources.getIdentifier(pictureImage.picture, "drawble", context.packageName)
                picture?.setImageResource(resouceId)
            } else {
                picture.setImageResource(R.drawable.ic_launcher_background)
            }
        }
    }
//    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var picture: ImageView = itemView.findViewById(R.id.iv_luggage_picture)
//    }
}