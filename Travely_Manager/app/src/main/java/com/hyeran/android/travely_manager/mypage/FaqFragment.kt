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
        season1.add("-\t상가에 단 1평 짜리라도 쓰이지 않는 공간을 활용, 가입비와 같은 일체의 비용 없이도 수익을 창출 할 수 있습니다.\n특히 tooc 서비스를 통한 매장으로의 고객유입은 곧 제품구매로 연결될 수 있습니다. 수익과 마케팅수단을 동시에 가져갈 수 있습니다.\n")

        val season2: MutableList<String> = ArrayList()
        season2.add("-\t상가에 CCTV 또는 잠금 장치가 설치된 보관공간이 존재한다면 제휴상가가 될 수 있습니다. \n제휴상가 게시판(Partners Contact) 또는 회사로의 연락을 통해 신청이 가능합니다.\n")

        val season3: MutableList<String> = ArrayList()
        season3.add("-\t예약을 한 고객에겐 QR 코드가 부여됩니다. \n고객이 방문할 시, 관리자용 앱을 통하여 QR코드를 스캔해 예약정보의 확인이 가능합니다. 예약정보를 확인한 뒤, 짐을 맡겨 주시면 됩니다. 짐을 찾으러 온 경우도 마찬가지로 QR코드를 스캔하여 보관 내역을 확인한 뒤, 짐을 반환하시면 됩니다.\n")

        val season4: MutableList<String> = ArrayList()
        season4.add("-\t베타 서비스 2개월의 기간을 제외하곤, 앱을 통해서만 결제가 가능합니다. 이를 위반 하였을 경우 불이익을 받을 수 있습니다.\n")


        header.add("tooc 호스트의 장점은 무엇인가요?")
        header.add("툭의 제휴상가가 되고 싶은데, 어떻게 해야 하나요?")
        header.add("호스트가 해야 하는 일은 무엇인가요?")
        header.add("상가에서 직접 결제해도 되나요?")


        body.add(season1)
        body.add(season2)
        body.add(season3)
        body.add(season4)

        expandable_faq.setAdapter(FaqAdapter(this.activity!!, faqexpandedView, header, body))

    }
}
