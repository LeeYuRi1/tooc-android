package com.hyeran.android.travely_user.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.MypageAreaLikeAdapter
import com.hyeran.android.travely_user.adapter.MypageLikeAdapter
import com.hyeran.android.travely_user.data.MypageAreaLikeData
import com.hyeran.android.travely_user.data.MypageLikeData
import kotlinx.android.synthetic.main.fragment_like.*

class LikeFragment : Fragment() {

    lateinit var mypageAreaLikeAdapter: MypageAreaLikeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_like, container, false)
        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        var dataList: ArrayList<MypageAreaLikeData> = ArrayList()
        dataList.add(MypageAreaLikeData("홍대입구역", 1))
        dataList.add(MypageAreaLikeData("강남역", 2))
        dataList.add(MypageAreaLikeData("수유역", 3))

        mypageAreaLikeAdapter = MypageAreaLikeAdapter(activity!!, dataList)
        rv_like_like.adapter = mypageAreaLikeAdapter
        rv_like_like.layoutManager = LinearLayoutManager(activity)

    }
}