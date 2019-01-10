package com.hyeran.android.tooc.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.tooc.MainActivity
import com.hyeran.android.tooc.R
import com.hyeran.android.tooc.adapter.MypageMyReviewAdapter
import com.hyeran.android.tooc.model.mypage.ReviewLookupData
import com.hyeran.android.tooc.network.ApplicationController
import com.hyeran.android.tooc.network.NetworkService
import kotlinx.android.synthetic.main.fragment_myreview.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyreviewFragment:Fragment() {
    lateinit var networkService : NetworkService

    lateinit var mypageMyReviewAdapter: MypageMyReviewAdapter

    lateinit var v : View
    var  reviewIdx : Int =0
    val dataList : ArrayList<ReviewLookupData> by lazy {
        ArrayList<ReviewLookupData>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_myreview, container, false)
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.addToBackStack(null)
        transaction.commit()
        init()
        getReviewLookupResponse()
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()

        iv_back_mypage.setOnClickListener {
            var fm = fragmentManager
            fragmentManager!!.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            (ctx as MainActivity).replaceFragment(MypageFragment())
            fm!!.popBackStack()
        }
    }

    private fun init() {
        networkService = ApplicationController.instance.networkService
    }

    private fun setRecyclerView() {
        mypageMyReviewAdapter = MypageMyReviewAdapter(activity!!, dataList)
        rv_review_myreview.adapter = mypageMyReviewAdapter

        val mLayoutManager = LinearLayoutManager(this.activity)
        mLayoutManager.reverseLayout = true   //리사이클러뷰 거꾸로
        mLayoutManager.stackFromEnd = true

        rv_review_myreview.setLayoutManager(mLayoutManager)
    }

    private fun getReviewLookupResponse() {
        var jwt : String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        var getReviewLookupResponse = networkService.getReviewLookupResponse(jwt)
        getReviewLookupResponse!!.enqueue(object : Callback<ArrayList<ReviewLookupData>>{
            override fun onFailure(call: Call<ArrayList<ReviewLookupData>>, t: Throwable) {
            }
            override fun onResponse(call: Call<ArrayList<ReviewLookupData>>, response: Response<ArrayList<ReviewLookupData>>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            var dataList_myreview : ArrayList<ReviewLookupData> = response.body()!!
                            if (dataList_myreview.size > 0) {
                                val position = mypageMyReviewAdapter.itemCount
                                mypageMyReviewAdapter.dataList.addAll(dataList_myreview)
                                mypageMyReviewAdapter.notifyItemInserted(position)

                                tv_review_cnt_myreview.setText(dataList_myreview.size.toString())
                            } else {

                            }
                            toast("리뷰 조회 성공")
                        }
                        204 -> {
                            toast("작성 리뷰 없음")
                        }
                        400 -> {
                            toast("잘못된 접근")
                        }
                        500 -> {
                            toast("서버에러")
                        }
                        204 -> {
                        }
                        else -> {
                            toast("error")
                        }
                    }
                }
            }
        })
    }




}