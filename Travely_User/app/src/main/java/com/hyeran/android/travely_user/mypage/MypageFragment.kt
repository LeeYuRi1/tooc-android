package com.hyeran.android.travely_user.mypage


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.MypageRecentStoreAdapter
import com.hyeran.android.travely_user.data.MypageRecentStoreData
import com.hyeran.android.travely_user.join.ExplanationActivity
import com.hyeran.android.travely_user.join.LoginActivity
import com.hyeran.android.travely_user.join.RecentstoreDetailFragment
import com.hyeran.android.travely_user.model.ProfileResponseData
import com.hyeran.android.travely_user.model.StoreInfoResponseData
import com.hyeran.android.travely_user.model.region.RegionResponseData
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.fragment_mypage.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {

    lateinit var networkService: NetworkService

    lateinit var mypageRecentStoreAdapter: MypageRecentStoreAdapter

    lateinit var v: View

    val dataList: ArrayList<StoreInfoResponseData> by lazy {
        ArrayList<StoreInfoResponseData>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_mypage, container, false)
        init()
        getProfileResponse()
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()
        setRecyclerView()
        getRecentStoreResponse()
    }

    private fun init() {
        networkService = ApplicationController.instance.networkService
    }

    private fun setRecyclerView() {
        mypageRecentStoreAdapter = MypageRecentStoreAdapter(activity!!, dataList)
        rv_recentstore_mypage.adapter = mypageRecentStoreAdapter
        rv_recentstore_mypage.layoutManager = LinearLayoutManager(activity)

    }

    private fun setClickListener() {

        iv_profile_mypage.setOnClickListener {
            replaceFragment(ProfileFragment())
        }
        layout_like_mypage.setOnClickListener {
            replaceFragment(LikeFragment())
        }
        layout_myreview_mypage.setOnClickListener {
            replaceFragment(MyreviewFragment())
//            val intent = Intent(this.activity, ExplanationActivity::class.java)
//            startActivity(intent)

        }
        iv_set_mypage.setOnClickListener {
            replaceFragment(SetFragment())
        }

    }

    fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frame_main, fragment)
        transaction.commit()
    }


    private fun getProfileResponse() {

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")

        val getProfileResponse = networkService.getProfileResponse(jwt)

        getProfileResponse!!.enqueue(object : Callback<ProfileResponseData> {
            override fun onFailure(call: Call<ProfileResponseData>, t: Throwable) {
            }

            override fun onResponse(call: Call<ProfileResponseData>, response: Response<ProfileResponseData>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            tv_name_mypage.text = response.body()!!.name
                            tv_mybag_cnt_mypage.text = response.body()!!.myBagCount.toString()
                            tv_favorite_cnt_mypage.text = response.body()!!.favoriteCount.toString()
                            tv_review_cnt_mypage.text = response.body()!!.reviewCount.toString()

                            Glide.with(this@MypageFragment)
                                    .load(response.body()!!.profileImg)
                                    .into(iv_profile_mypage)

                            toast("프로필 조회 성공")
                        }
                        500 -> {
                            toast("서버 에러")
                        }
                        else -> {
                            toast("error")
                        }
                    }
                }
            }

        })
    }

    private fun getRecentStoreResponse() {
        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")


        val getProfileResponse = networkService.getProfileResponse(jwt)

        getProfileResponse!!.enqueue(object : Callback<ProfileResponseData> {
            override fun onFailure(call: Call<ProfileResponseData>, t: Throwable) {
            }

            override fun onResponse(call: Call<ProfileResponseData>, response: Response<ProfileResponseData>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            var dataList_recent: ArrayList<StoreInfoResponseData> = response.body()!!.storeInfoResponseDtoList

                            if (dataList_recent.size > 0) {
                                val position = mypageRecentStoreAdapter.itemCount
                                mypageRecentStoreAdapter.dataList.addAll(dataList_recent)
                                mypageRecentStoreAdapter.notifyItemInserted(position)
                            } else {

                            }

                            toast("최근 예약 상가 조회 성공")
                        }
                        500 -> {
                            toast("서버 에러")
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

