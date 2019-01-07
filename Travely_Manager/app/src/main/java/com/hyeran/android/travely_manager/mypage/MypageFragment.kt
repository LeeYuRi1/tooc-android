package com.hyeran.android.travely_manager.mypage

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hyeran.android.travely_manager.R
import com.hyeran.android.travely_manager.R.id.iv_set_mypage
import com.hyeran.android.travely_manager.ReviewFragment
import com.hyeran.android.travely_manager.login.ExplanationActivity
import com.hyeran.android.travely_manager.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_mypage.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_mypage, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()


    }

    private fun setClickListener() {
        iv_set_mypage.setOnClickListener {
            //replaceFragment(SetFragment())
            val intent = Intent(this.activity, LoginActivity::class.java)
            startActivity(intent)
        }
//
//        btn_review_mypage.setOnClickListener {
//            replaceFragment(ReviewFragment())
//        }
//
//        btn_whole_statistics_mypage.setOnClickListener {
//            replaceFragment(WholeStatisticsFragment())
//        }

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
}