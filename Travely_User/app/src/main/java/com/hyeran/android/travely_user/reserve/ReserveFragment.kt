package com.hyeran.android.travely_user.reserve

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reserve.view.*
import org.jetbrains.anko.support.v4.toast
import android.widget.CompoundButton
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.dialog.ReserveCancelDialog
import com.hyeran.android.travely_user.reserve_state.ReserveStateFragment
import com.hyeran.android.travely_user.dialog.ReserveCompleteDialog
import kotlinx.android.synthetic.main.fragment_reserve.*
import org.jetbrains.anko.support.v4.ctx

class ReserveFragment : Fragment() {

    var carrier_amount: Int = 0
    var etc_amount: Int = 0
    var carrier_price: Int = 0
    var etc_price: Int = 0

    var smmddee: String? = null
    var tmmddee: String? = null
    var shh: String? = null
    var smm: String? = null
    var thh: String? = null
    var tmm: String? = null
//
//
//    fun getTimeSettingDialog(tsmmddee: String, tshh: String, tsmm: String, ttmmddee: String, tthh: String, ttmm: String) {
//        smmddee = tsmmddee
//        shh = tshh
//        smm = tsmm
//        tmmddee = ttmmddee
//        thh = tthh
//        tmm = ttmm
//
//        tv_store_date_reserve.text = smmddee.toString()
//        tv_store_hour_reserve.text = shh.toString()
//        tv_store_minute_reserve.text = smm.toString()
//        tv_take_date_reserve.text = tmmddee.toString()
//        tv_take_hour_reserve.text = thh.toString()
//        tv_take_minute_reserve.text = tmm.toString()
//    }

    companion object {
        //static in java
        private var instance: ReserveFragment? = null

        fun getInstance(smmddee: String, shh: String, smm: String, tmmddee: String, thh: String, tmm: String): ReserveFragment {
            if (instance == null) {
                instance = ReserveFragment().apply {
                    //초기화와 동시에 ㅓ어떤작업을 해주기위해 aply 사용
                    arguments = Bundle().apply {
                        putString("smmddee", smmddee)
                        putString("shh", shh)
                        putString("smm", smm)
                        putString("tmmddee", tmmddee)
                        putString("thh", thh)
                        putString("tmm", tmm)

                        var a = smmddee
                        var b = shh
                        var c = smm
                        var d = tmmddee
                        var e = thh
                        var f = tmm

                        tv_store_date_reserve.text = a
                        tv_store_hour_reserve.text = b
                        tv_store_minute_reserve.text = c
                        tv_take_date_reserve.text = d
                        tv_take_hour_reserve.text =e
                        tv_take_minute_reserve.text = f

                    }
                }
            }
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //argumnet가 null이면 수행안되게하는 코드
            smmddee = it.getString("smmddeee")//it은 번들을 참조하는 것 바로위의 argument, argument가 번들이다
            shh = it.getString("shh")
            smm = it.getString("smm")
            tmmddee = it.getString("tmmddeee")//it은 번들을 참조하는 것 바로위의 argument, argument가 번들이다
            thh = it.getString("thh")
            tmm = it.getString("tmm")
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_reserve, container, false)

        var args : Bundle? = arguments
        args!!.getString("smmddeee", "")
        args!!.getString("shh", "")
        args!!.getString("smm", "")
        args!!.getString("tmmddeee", "")
        args!!.getString("thh", "")
        args!!.getString("tmm", "")

        v.tv_store_date_reserve.text = args!!.getString("smmddeee")
        v.tv_store_hour_reserve.text = args!!.getString("shh")
        v.tv_store_minute_reserve.text = args!!.getString("smm")
        v.tv_take_date_reserve.text = args!!.getString("tmmddeee")
        v.tv_take_hour_reserve.text = args!!.getString("thh")
        v.tv_take_minute_reserve.text = args!!.getString("tmm")




        setOnClickListener(v)
        toast("@@@@@@@@@@@@@@@@@")

        return v
    }

    override fun onResume() {
        super.onResume()

        toast("ASDASDASDDASASDASDASD")
    }

    fun setOnClickListener(v: View) {
        v.btn_alldate_reserve.setOnClickListener {
            val dialog = ReserveTimeSettintDialog(ctx)
            dialog.show()
        }

        //tv_result_amount_carrier_reserve, tv_result_amount_etc_reserve 뺌
        v.cb_carrier_reserve.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (isChecked) {
                    v.linear_carrier_more_reserve.visibility = View.VISIBLE
                    carrier_price = 3500
                    v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                    carrier_amount = 1
                    //v.tv_result_amount_carrier_reserve.text = carrier_amount.toString()

                    v.iv_carrier_amount_up_reserve.setOnClickListener {
                        carrier_amount = Integer.parseInt(v.tv_carrier_changing_amount_reserve.text as String?)
                        carrier_amount++
                       // v.tv_result_amount_carrier_reserve.text = carrier_amount.toString()
                        v.tv_carrier_changing_amount_reserve.text = carrier_amount.toString()
                        v.tv_carrier_amount_reserve.text = carrier_amount.toString()
                        carrier_price = carrier_amount * 3500
                        v.tv_price_carrier_reserve.text = carrier_price.toString()
                        v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                    }
                    v.iv_carrier_amount_down_reserve.setOnClickListener {
                        carrier_amount = Integer.parseInt(v.tv_carrier_amount_reserve.text as String?)
                        if ((carrier_amount - 1) >= 0) {
                            carrier_amount--
                           // v.tv_result_amount_carrier_reserve.text = carrier_amount.toString()
                            v.tv_carrier_changing_amount_reserve.text = carrier_amount.toString()
                            v.tv_carrier_amount_reserve.text = carrier_amount.toString()
                            carrier_price = carrier_amount * 3500
                            v.tv_price_carrier_reserve.text = carrier_price.toString()
                            v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                        }
                    }
                } else {
                    v.linear_carrier_more_reserve.visibility = View.GONE
                    carrier_price = 0
                    v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                    carrier_amount = 0
                   // v.tv_result_amount_carrier_reserve.text = carrier_amount.toString()
                }
            }
        })

        v.cb_etc_reserve.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (isChecked) {
                    v.linear_etc_more_reserve.visibility = View.VISIBLE
                    etc_price = 3500
                    v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                    etc_amount = 1
                   // v.tv_result_amount_etc_reserve.text = etc_amount.toString()

                    v.iv_etc_amount_up_reserve.setOnClickListener {
                        etc_amount = Integer.parseInt(v.tv_etc_changing_amount_reserve.text as String?)
                        etc_amount++
                        v.tv_etc_changing_amount_reserve.text = etc_amount.toString()
                        v.tv_etc_amount_reserve.text = etc_amount.toString()
                        etc_price = etc_amount * 3500
                        v.tv_price_etc_reserve.text = etc_price.toString()
                        v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                      //  v.tv_result_amount_etc_reserve.text = etc_amount.toString()
                    }
                    v.iv_etc_amount_down_reserve.setOnClickListener {
                        etc_amount = Integer.parseInt(v.tv_etc_amount_reserve.text as String?)
                        if ((etc_amount - 1) >= 0) {
                            etc_amount--
                            v.tv_etc_changing_amount_reserve.text = etc_amount.toString()
                            v.tv_etc_amount_reserve.text = etc_amount.toString()
                            etc_price = etc_amount * 3500
                            v.tv_price_etc_reserve.text = etc_price.toString()
                            v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                           // v.tv_result_amount_etc_reserve.text = etc_amount.toString()
                        }
                    }
                } else {
                    etc_price = 0
                    v.tv_total_price_reserve.text = (carrier_price + etc_price).toString()
                    v.linear_etc_more_reserve.visibility = View.GONE
                    etc_amount = 0
                   // v.tv_result_amount_etc_reserve.text = etc_amount.toString()
                }
            }
        })

        v.btn_reserve_reserve.setOnClickListener {
            if (v.cb_confirm_reserve.isChecked) {
                if (v.cb_carrier_reserve.isChecked || v.cb_etc_reserve.isChecked) {
                    if (v.rb_kakaopay_reserve.isChecked || v.rb_cash_reserve.isChecked) {
                        if (v.rb_kakaopay_reserve.isChecked) {
                            val intent: Intent = Intent(context, KakaopayWebView::class.java)
                            startActivityForResult(intent, 9999)
                        }
                        if (v.rb_cash_reserve.isChecked) {
                            ReserveCompleteDialog(context).show()
                        }
                    } else {
                        toast("결제 방법을 선택해주세요.")
                    }
                } else {
                    toast("짐 종류를 선택해주세요.")
                }
            } else {
                toast("결제 동의를 체크해주세요.")
            }
        }
    }

}