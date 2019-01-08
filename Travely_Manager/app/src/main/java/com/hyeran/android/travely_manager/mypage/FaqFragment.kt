package com.hyeran.android.travely_manager.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.hyeran.android.travely_manager.R
import com.hyeran.android.travely_user.mypage.FaqAdapter
import kotlinx.android.synthetic.main.fragment_faq.*


class FaqFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_faq, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        expandable()
    }

    private fun expandable() {
        val header: MutableList<String> = ArrayList()
        val body: MutableList<MutableList<String>> = ArrayList()

        var faqexpandedView: ExpandableListView? = null

        faqexpandedView = view!!.findViewById(R.id.expandable_faq)


        val season1: MutableList<String> = ArrayList()
        season1.add("11111111111111111")

        val season2: MutableList<String> = ArrayList()
        season2.add("222222")

        val season3: MutableList<String> = ArrayList()
        season3.add("333333333333")


        header.add("트레블리는 어떤 앱인가요?")
        header.add("내 짐은 누가 보관하나요?")
        header.add("짐은 얼마나 오랫동안 보관할 수 있나요?")


        body.add(season1)
        body.add(season2)
        body.add(season3)

        expandable_faq.setAdapter(FaqAdapter(this.activity!!, faqexpandedView, header, body))

    }
}
