package com.hyeran.android.travely_user.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.QuestionAdapter
import com.hyeran.android.travely_user.data.QuestionData
import kotlinx.android.synthetic.main.fragment_question.*
import org.jetbrains.anko.support.v4.ctx

class QuestionFragment : Fragment() {

    lateinit var questionAdapter: QuestionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_question, container, false)
        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()
        setClickListener()
    }

    private fun setRecyclerView() {
        var dataList: ArrayList<QuestionData> = ArrayList()
        dataList.add(QuestionData("IMG_047.jpg"))
        dataList.add(QuestionData("IMG_049.jpg"))
        dataList.add(QuestionData("IMG_051.jpg"))

        questionAdapter = QuestionAdapter(activity!!, dataList)
        rv_file_question.adapter = questionAdapter
        rv_file_question.layoutManager = LinearLayoutManager(activity)

    }

    private fun setClickListener() {
        iv_back_question.setOnClickListener {
            (ctx as MainActivity).replaceFragment(SetFragment())
        }
    }

}