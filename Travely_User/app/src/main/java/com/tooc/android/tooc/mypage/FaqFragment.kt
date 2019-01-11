package com.tooc.android.tooc.mypage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.tooc.android.tooc.MainActivity
import com.tooc.android.tooc.R
import com.tooc.android.tooc.adapter.FaqAdapter
import kotlinx.android.synthetic.main.fragment_faq.*
import org.jetbrains.anko.support.v4.ctx

class FaqFragment :Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_faq, container, false)
        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        expandable()

        iv_back_faq.setOnClickListener {
            (ctx as MainActivity).replaceFragment(SetFragment())
        }
    }

    private fun expandable() {
        val header : MutableList<String> = ArrayList()
        val body : MutableList<MutableList<String>> = ArrayList()

        var faqexpandedView : ExpandableListView? = null

        faqexpandedView = view!!.findViewById(R.id.expandable_faq)


        val season1 : MutableList<String> = ArrayList()
        season1.add("-\tCCTV와 별도의 잠금 장치가 있는 공간을 구비한 상가만이 tooc의 제휴상가가 될 수 있습니다. \n" +
                "-\t점주는 보관과정에서 보관할 고객의 짐을 반드시 사진촬영 해야 하며, 짐에는 보안태그가 붙여집니다. 고객께서는 점주로부터 짐을 찾을 시, 촬영된 짐 사진을 확인하여 짐의 본인여부와 짐의 상태를 확인 할 수 있습니다. 또한 보안태그를 통해 고객이 짐을 찾으러 오기전까지 짐을 개봉하지 않았음을 확인할 수 있습니다.\n")

        val season2 : MutableList<String> = ArrayList()
        season2.add("-\t물품 보관가방에 보관 시, 가방에 수용 가능한 양만이 보관 가능합니다. (가로 65 폭 15 높이 47) 하나의 보관 가방에 [중형 백팩 2개] 또는 [일반 쇼핑백 5개] 정도가 수용 가능합니다. \n" +
                "-\t물품 보관가방에 들어갈 수 없는 짐인 캐리어와 대형 백팩은 단일 형태로 보관 됩니다.\n")

        val season3 : MutableList<String> = ArrayList()
        season3.add("-\t신분증, 여권, 화폐(돈), 고가품, 위험물질(가연성물질), 폐기물, 밀수품, 상한음식, 오염된 품목, 강한 냄새를 방출하는 물질 등은 보관이 불가합니다.\n")

        val season4 : MutableList<String> = ArrayList()
        season4.add("-\t제휴상가는 서울을 중심으로 매우 빠르게 확보 중입니다. \n빠른 시일 내, 전국에서 서비스를 만나 보실 수 있습니다. \n서비스 제휴상가 추천은 제휴상가 게시판(Partner Contact) 문의 또는 회사로의 연락을 통해 가능합니다.\n")

        val season5 : MutableList<String> = ArrayList()
        season5.add("-\t예정된 맡길 시간보다 더 일찍 짐을 보관하는 것이 가능합니다.\n요금은 상가에 짐을 보관한 순간부터 시간에 따라 자동으로 책정됩니다. 예정된 보관시간을 초과시, 추가 요금이 발생합니다.\n" +
                "-\t예정된 맡길 시간 30분전에 앱을 통하여 예약 확인 알람이 전송되며, 예정된 맡길 시간을 지났을 경우 예약은 자동 취소됩니다. \n")

        val season6 : MutableList<String> = ArrayList()
        season6.add("-\t고객께서 짐을 찾아갈 때까지 제휴 상가에서 보관이 지속되며, 이에 따른 추가 비용이 발생 할 수 있습니다. \n짐을 찾아가지 못하는 경우, 고객게시판(Customers Contact)을 통해 배송 처리가 가능합니다.  \n")

        val season7 : MutableList<String> = ArrayList()
        season7.add("-\t보안문제로 인하여 불가합니다. \n문제 발생 시 고객게시판(Customers Contact)을 통하여 문의 바랍니다.\n")

        val season8 : MutableList<String> = ArrayList()
        season8.add("-\t고객 당사자임을 증빙할 수 있는 신분증(여권포함), 이메일 주소, 핸드폰 번호 등을 제시할 경우 가능합니다.\n")



        header.add("상가에 마음 놓고 짐을 보관할 수 있을까요?")
        header.add("짐의 크기에는 제한이 있나요?")
        header.add("보관이 안되는 물건도 있나요?")
        header.add("서비스를 이용할 수 있는 상가가 몇 개 없어요")
        header.add("예약한 보관을 시간을 지키지 않아도 상관 없나요?")
        header.add("사정이 생겨 짐을 못 찾아가는 경우가 생기면 어떻게 해요?")
        header.add("예약한 본인이 아닌, 다른 사람이 짐을 보관하거나 찾아갈 수 있나요?")
        header.add("핸드폰을 분실한 경우 짐을 찾을 수 있나요?")


        body.add(season1)
        body.add(season2)
        body.add(season3)
        body.add(season4)
        body.add(season5)
        body.add(season6)
        body.add(season7)
        body.add(season8)

        expandable_faq.setAdapter(FaqAdapter(this.activity!!,faqexpandedView,header,body))

    }
}