package com.hyeran.android.travely_manager.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.travely_manager.R
import kotlinx.android.synthetic.main.fragment_set.*


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
        btn_faq_set.setOnClickListener {
            replaceFragment(FaqFragment())
        }
        btn_access_terms_set.setOnClickListener {
            replaceFragment(AccessTermsFragment())
        }
        btn_question_set.setOnClickListener {
            replaceFragment(QuestionFragment())
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frame_main, fragment)
        transaction.commit()
    }
}