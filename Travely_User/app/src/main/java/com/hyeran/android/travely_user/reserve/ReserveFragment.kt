package com.hyeran.android.travely_user.reserve

import android.app.Dialog
import android.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reserve.view.*
import org.jetbrains.anko.support.v4.toast
import android.widget.CompoundButton
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.dialog.ReserveCompleteDialog
import org.jetbrains.anko.support.v4.ctx
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReserveFragment : Fragment() {

    var carrier_amount: Int = 0
    var etc_amount: Int = 0
    var carrier_price: Int = 0
    var etc_price: Int = 0

    var smmddee:String? =null
    var tmmddee:String? =null
    var svalue:Int = 0
    var tvalue:Int=0
    var snumhh : Int=0
    var snummm :Int = 0
    var tnumhh :Int =0
    var tnummm : Int=0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_reserve, container, false)


        var args : Bundle? = arguments

        var rightNow = Calendar.getInstance()
        var dateFormat =SimpleDateFormat("MMM dd일 (EE)")

        var defaultHourValue = rightNow.get(Calendar.HOUR_OF_DAY)
        var defaultMinuteValue = rightNow.get(Calendar.MINUTE)

        v.tv_store_date_reserve.text = args!!.getString("smmddee",dateFormat.format(rightNow.time))
        snumhh = args!!.getInt("shh",defaultHourValue)
        if(snumhh<10){
            v.tv_store_hour_reserve.text = "0" + snumhh.toString()
        }else  v.tv_store_hour_reserve.text = snumhh.toString()

        snummm = args!!.getInt("smm",defaultMinuteValue)
        if(snummm<10){
            v.tv_store_minute_reserve.text = "0" + snummm.toString()
        }else  v.tv_store_minute_reserve.text = snummm.toString()

        v.tv_take_date_reserve.text = args!!.getString("tmmddee",dateFormat.format(rightNow.time))
        tnumhh = args!!.getInt("thh",defaultHourValue)
        if(tnumhh<10){
            v.tv_take_hour_reserve.text = "0" + tnumhh.toString()
        }else  v.tv_take_hour_reserve.text = tnumhh.toString()

        tnummm = args!!.getInt("tmm",defaultMinuteValue)
        if(tnummm<10){
            v.tv_take_minute_reserve.text = "0" + tnummm.toString()
        }else  v.tv_take_minute_reserve.text = tnummm.toString()

        smmddee = v.tv_store_date_reserve.text.toString()
        tmmddee=v.tv_take_date_reserve.text.toString()
        svalue = args!!.getInt("svalue")
        tvalue = args!!.getInt("tvalue")
        setOnClickListener(v)
        return v
    }

    fun setOnClickListener(v: View) {
        v.btn_alldate_reserve.setOnClickListener {
            var timeArray : ArrayList<Any> = arrayListOf(smmddee.toString(),snumhh,snummm,tmmddee.toString(),tnumhh,tnummm
            ,svalue,tvalue)
            //toast(snumhh)
            val dialog = ReserveTimeSettintDialog(ctx,timeArray)
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