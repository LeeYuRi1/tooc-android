package com.hyeran.android.tooc.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.data.QuestionData
import com.hyeran.android.tooc.mypage.QuestionFragment
import java.util.ArrayList

class QuestionAdapter(val ctx: Context, val dataList : ArrayList<QuestionData>) : RecyclerView.Adapter<QuestionAdapter.Holder>() {

    var questionFragment = QuestionFragment()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAdapter.Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.item_question , parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: QuestionAdapter.Holder, position: Int) {
        holder.file_name.text = dataList[position].filename

        holder.file_x.setOnClickListener {
            //notifyItemRemoved(position)
            //dataList.remove(position)
            //questionFragment.FileXClick()
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var file_name : TextView = itemView.findViewById(R.id.tv_filename_question) as TextView
        var file_x : ImageView = itemView.findViewById(R.id.iv_x_question) as ImageView
    }
}