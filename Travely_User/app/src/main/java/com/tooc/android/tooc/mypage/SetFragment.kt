package com.tooc.android.tooc.mypage

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tooc.android.tooc.MainActivity
import com.tooc.android.tooc.R
import kotlinx.android.synthetic.main.fragment_set.*
import org.jetbrains.anko.support.v4.ctx

class SetFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_set, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        btn_whatistooc_set.setOnClickListener {
            replaceFragment(ToocFragment())
        }
        btn_howtouse_set.setOnClickListener {
            val intent = Intent(ctx, HowtouseActivity::class.java)
            startActivity(intent)
        }
        btn_faq_set.setOnClickListener {
            replaceFragment(FaqFragment())
        }
        btn_access_terms_set.setOnClickListener {
            replaceFragment(AccessTermsFragment())
        }
        btn_question_set.setOnClickListener {
            replaceFragment(QuestionFragment())
        }

        iv_back_set.setOnClickListener {
            var fm = fragmentManager
            (ctx as MainActivity).replaceFragment(MypageFragment())
            fragmentManager!!.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            fm!!.popBackStack()
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frame_main, fragment)
        transaction.commit()
    }
}