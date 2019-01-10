package com.tooc.android.tooc.mypage

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tooc.android.tooc.MainActivity
import com.tooc.android.tooc.R
import com.tooc.android.tooc.adapter.MypageAreaLikeAdapter
import com.tooc.android.tooc.adapter.MypageMyReviewAdapter
import com.tooc.android.tooc.model.mypage.FavoriteResponseData
import com.tooc.android.tooc.network.ApplicationController
import com.tooc.android.tooc.network.NetworkService
import kotlinx.android.synthetic.main.fragment_like.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeFragment : Fragment() {

    lateinit var networkService: NetworkService

    lateinit var mypageAreaLikeAdapter: MypageAreaLikeAdapter

    var storeIdx: Int = 0


    val dataList: ArrayList<FavoriteResponseData> by lazy {
        ArrayList<FavoriteResponseData>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_like, container, false)
        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()
        getFavoriteResponse()

        iv_back_like.setOnClickListener {
            var fm = fragmentManager
            fragmentManager!!.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            (ctx as MainActivity).replaceFragment(MypageFragment())
            fm!!.popBackStack()
        }
    }

    private fun setRecyclerView() {
        mypageAreaLikeAdapter = MypageAreaLikeAdapter(activity!!, dataList)
        rv_like_like.adapter = mypageAreaLikeAdapter
        rv_like_like.layoutManager = LinearLayoutManager(activity)
    }

    fun getLikeStoreIdx(storeIdx : Int) {

        this.storeIdx = storeIdx

        var intent : Intent = Intent()
        intent.putExtra("storeIdx", storeIdx)
//        activity!!.setResult(2, intent)
//        activity!!.finish()
    }

    private fun getFavoriteResponse(){

        networkService = ApplicationController.instance!!.networkService

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")

        val getfavoriteResponse = networkService.getFavoriteResponse(jwt)

        getfavoriteResponse!!.enqueue(object : Callback<ArrayList<FavoriteResponseData>> {
            override fun onFailure(call: Call<ArrayList<FavoriteResponseData>>, t: Throwable) {
            }

            override fun onResponse(call: Call<ArrayList<FavoriteResponseData>>, response: Response<ArrayList<FavoriteResponseData>>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            var dataList_favorite: ArrayList<FavoriteResponseData> = response.body()!!
                            if (dataList_favorite.size > 0) {
                                val position = mypageAreaLikeAdapter.itemCount
                                mypageAreaLikeAdapter.dataList.addAll(dataList_favorite)
                                mypageAreaLikeAdapter.notifyItemInserted(position)
                            }else { }

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