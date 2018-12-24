package com.hyeran.android.travely_user.mypage


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.adapter.MypageRecentStoreAdapter
import com.hyeran.android.travely_user.data.MypageRecentStoreData
import com.hyeran.android.travely_user.join.LoginActivity
import com.hyeran.android.travely_user.join.WelcomeActivity
import kotlinx.android.synthetic.main.fragment_mypage.*

class MypageFragment : Fragment() {

    lateinit var mypageRecentStoreAdapter: MypageRecentStoreAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_mypage, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()
        setRecyclerView()
        writeReview()
    }

    private fun setRecyclerView() {
        var dataList: ArrayList<MypageRecentStoreData> = ArrayList()
        dataList.add(MypageRecentStoreData("프로마치", "성북구 안암동 123번지", "10:00 - 23:00", "02-111-1111", "4.3"))
        dataList.add(MypageRecentStoreData("프로마치2", "성북구 안암동 123번지", "11:00 - 22:00", "02-222-2222", "4.3"))
        dataList.add(MypageRecentStoreData("프로마치3", "성북구 안암동 123번지", "12:00 - 23:00", "02-222-3333", "4.3"))
        dataList.add(MypageRecentStoreData("프로마치", "성북구 안암동 123번지", "10:00 - 23:00", "02-111-1111", "4.3"))
        dataList.add(MypageRecentStoreData("프로마치2", "성북구 안암동 123번지", "11:00 - 22:00", "02-222-2222", "4.3"))
        dataList.add(MypageRecentStoreData("프로마치3", "성북구 안암동 123번지", "12:00 - 23:00", "02-222-3333", "4.3"))

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
        }

        iv_set_mypage.setOnClickListener {
            replaceFragment(SetFragment())
//            val intent = Intent(this.activity, LoginActivity::class.java)
//            startActivity(intent)
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frame_main, fragment)
        transaction.commit()
    }

    private fun writeReview() {
        layout_state_mypage.setOnClickListener {
            WriteReviewDialog(context).show()
        }
    }


}
