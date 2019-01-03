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
import com.hyeran.android.travely_user.join.LoginActivity
import com.hyeran.android.travely_user.join.RecentstoreDetailFragment
import com.hyeran.android.travely_user.model.ProfileResponseData
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.fragment_mypage.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {

    lateinit var networkService: NetworkService

    lateinit var mypageRecentStoreAdapter: MypageRecentStoreAdapter

    lateinit var v : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_mypage, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        getProfileResponse()
        setClickListener()
        setRecyclerView()
    }

    private fun init() {
        networkService = ApplicationController.instance.networkService
    }

    private fun setRecyclerView() {
        var dataList: ArrayList<MypageRecentStoreData> = ArrayList()
        dataList.add(MypageRecentStoreData("동대문엽기떡볶이 홍대점", "성북구 안암동 123번지", "10:00 - 23:00"))
        dataList.add(MypageRecentStoreData("롭스 홍대2호점", "성북구 안암동 123번지", "11:00 - 22:00"))
        dataList.add(MypageRecentStoreData("프로마치", "성북구 안암동 123번지", "12:00 - 23:00"))

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
//            val intent = Intent(this.activity, LoginActivity::class.java)
//            startActivity(intent)


            //WriteReviewDialog(context).show()
        }
        iv_set_mypage.setOnClickListener {
            replaceFragment(SetFragment())
        }

    }

    private fun replaceFragment(fragment: Fragment) {
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

}
