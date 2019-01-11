package com.hyeran.android.travely_manager

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reserve_detail.*
import android.util.Log
import com.bumptech.glide.Glide
import com.hyeran.android.travely_manager.db.SharedPreferencesController
import com.hyeran.android.travely_manager.network.ApplicationController
import com.hyeran.android.travely_manager.network.NetworkService
import kotlinx.android.synthetic.main.fragment_reserve_detail.view.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import android.graphics.Bitmap
import android.app.Activity.RESULT_OK
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import com.hyeran.android.travely_manager.model.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*


class ReserveDetailFragment : Fragment() {

    lateinit var networkService: NetworkService
    lateinit var v: View
    var reserveCode: String = ""
    var reserveIdx: String = ""

    private val REQ_CODE_SELECT_IMAGE = 100

    lateinit var bitmapRVAdapter: BitmapRVAdapter
    lateinit var imgUrlRVAdapter: ImgUrlRVAdapter

    var pick_reserveIdx = 0.toLong()
    //

    lateinit var bitmap: BitmapData
    lateinit var bitmapArray: ArrayList<BitmapData>

    var imgUrl = ArrayList<String>()
//     lateinit var bagImgRequestDtoData : BagImgRequestDtoData

    var dataList: ArrayList<BitmapData> = ArrayList()
    lateinit var bagImgDtos : ArrayList<BagImgDtos>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_reserve_detail, container, false)

        networkService = ApplicationController.instance.networkService

        (ctx as MainActivity).selectedTabChangeColor(1)

//        v.btn_store_reserve_detail.setOnClickListener {
//
//            if(!cb_pay_agree.isChecked) {
//                CashInfoDialog(context).show()
//            }
//            else{
//                StorePhotoDialog(context).show()
//            }
//        }

        var bundle: Bundle? = arguments
        reserveCode = bundle!!.getString("reserveCode", "")
        reserveIdx = bundle!!.getString("reserveIdx", "")
        Log.d("TAGGGG", reserveIdx)
        v.tv_number_reservedetail.text = reserveCode

        if (reserveCode != "") {
            getStoreIdxReserveCodeResponse()
        } else {
            getDetailReserveResponse()
        }


//        getStoreIdxReserveCodeResponse()

        return v
    }

    private fun setClickListener() {
        btn_storage_reservedetail.setOnClickListener {
            putStorePickupResponse()
            layout_storage_reservedetail.visibility = View.GONE
            layout_pickup_reservedetail.visibility = View.VISIBLE
            layout_picture_reservedetail.visibility = View.GONE

            imgUrlRVAdapter = ImgUrlRVAdapter(context, bagImgDtos)
            rv_luggage_picture.adapter = imgUrlRVAdapter
            var mLayoutManager = LinearLayoutManager(context)
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            rv_luggage_picture.layoutManager = mLayoutManager
        }

        btn_pickup_reservedetail.setOnClickListener {
            putStorePickupResponse()
            (ctx as MainActivity).replaceFragment(ReserveStorageListFragment())
        }
        btn_picture_reservedetail.setOnClickListener {
            takePhoto()
        }

//        if(layout_storage_reservedetail.visibility == View.VISIBLE){
//            btn_storage_reservedetail.setOnClickListener {
//                if (cb_confirm_reservedetail.isChecked) {
//                    putStorePickupResponse()
//                    getStoreIdxReserveCodeResponse()
//                } else {
//                    toast("동의해주세요")
//                }
//            }
//        } else if(layout_pickup_reservedetail.visibility == View.VISIBLE) {
//
//        }
//
//
//        btn_pickup_reservedetail.setOnClickListener {
//            putStorePickupResponse()
//            getStoreIdxReserveCodeResponse()
//        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode === REQ_CODE_SELECT_IMAGE && resultCode === RESULT_OK) {

            if (data != null) {

                val extras = data!!.getExtras()
                val imageBitmap = extras.get("data") as Bitmap

//
                val bmp: Bitmap? = imageBitmap
                val stream = ByteArrayOutputStream()
                bmp!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()

                var f: File = File(context!!.cacheDir, "tooc.jpg")
                f.createNewFile()
                Log.d("@@@@absoultuePath", f.absolutePath.toString())
                Log.d("@@@@canonicalPath", f.canonicalPath.toString())
                Log.d("@@@@name", f.extension)
                var fos: FileOutputStream = FileOutputStream(f)
                fos.write(byteArray)
                fos.flush()
                fos.close()

                var file = context!!.cacheDir.toString() + "tooc"
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
//
//            bitmapArray.add(BitmapData(imageBitmap))
//
//
//            bitmapRVAdapter = BitmapRVAdapter(context, bitmapArray)
//            v.rv_luggage_picture.adapter = bitmapRVAdapter
//            v.rv_luggage_picture.layoutManager = LinearLayoutManager(context)
//
//            photoStorageDetailRVAdapter = PhotoStorageDetailRVAdapter(context, bitmapArray)
//            rv_by_area_temp.adapter = locationRVAdapter
//            rv_by_area_temp.layoutManager = LinearLayoutManager(this)

//            photoStorageDetailRVAdapter = PhotoStorageDetailRVAdapter(context, bitmapArray)
//            rv_by_area_temp.adapter = locationRVAdapter
//            rv_by_area_temp.layoutManager = LinearLayoutManager(this)
                val photoBody = RequestBody.create(MediaType.parse("image/jpg"), byteArray)
                //첫번째 매개변수 String을 꼭! 꼭! 서버 API에 명시된 이름으로 넣어주세요!!!
                var mImage = MultipartBody.Part.createFormData("data", f.name, photoBody);
//            var mImage = MultipartBody.Part.createFormData("data", , photoBody);
                postImgResponse(mImage)


                dataList.add(BitmapData(imageBitmap))

                bitmapRVAdapter = BitmapRVAdapter(context, dataList)
                v.rv_luggage_picture.adapter = bitmapRVAdapter
                var mLayoutManager = LinearLayoutManager(context)
                mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                v.rv_luggage_picture.layoutManager = mLayoutManager



            } else {
                toast("기본 카메라로 설정해주세요.")
            }
        }
    }


    fun takePhoto() {
        var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQ_CODE_SELECT_IMAGE)
    }

    private fun postImgResponse(a: MultipartBody.Part) {

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        Log.d("@@@@@@", a.headers().toString())
        Log.d("@@@@@@", a.body().toString())
        val postImgResponse = networkService.postImgResponse(jwt, a)

        postImgResponse!!.enqueue(object : Callback<BagImgDtos> {
            override fun onFailure(call: Call<BagImgDtos>, t: Throwable) {
            }

            override fun onResponse(call: Call<BagImgDtos>, response: Response<BagImgDtos>) {

                response?.let {
                    when (it.code()) {
                        200 -> {
                            toast("OK")
                        }
                        201 -> {
                            toast("저장 성공")
                            imgUrl.add(response.body()!!.bagImgUrl)
                                                                                                                                                                                                                                                                                                                                                Log.d("@@@@@@@@@@", response.body().toString())

                        }
                        401 -> {
                            toast("권한 없음")
                        }
                        500 -> {
                            toast("서버 에러")
                        }
                        else -> {
                            toast("error" + it.code())
                        }
                    }
                }
            }
        })
    }

    private fun getStoreIdxReserveCodeResponse() {

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        var storeIdx: Int = SharedPreferencesController.instance!!.getPrefIntegerData("storeIdx")

        val getStoreIdxReserveCodeResponse = networkService.getStoreIdxReserveCodeResponse(jwt, reserveCode, storeIdx)

        getStoreIdxReserveCodeResponse!!.enqueue(object : Callback<ReserveDetailResponseData> {
            override fun onFailure(call: Call<ReserveDetailResponseData>, t: Throwable) {
            }

            override fun onResponse(call: Call<ReserveDetailResponseData>, response: Response<ReserveDetailResponseData>) {
                Log.d("@@@@@@@@@@@", "reserveCode: " + reserveCode + ", storeIdx: " + storeIdx)

                response?.let {
                    when (it.code()) {
                        200 -> {
                            reserveIdx = response.body()!!.reserveIdx.toString()
                            //toast("예약코드 조회 성공")
                            tv_name_reservedetail.text = response.body()!!.userName
                            tv_phone_reservedetail.text = response.body()!!.userPhone
                            if (response.body()!!.payType == "CASH") {
                                tv_payment_reservedetail.text = "현금"
                            } else {
                                tv_payment_reservedetail.text = "카카오페이"
                            }
                            var bagDtoList_: ArrayList<BagDtos> = response.body()!!.bagDtoList
                            var totalCnt = 0
                            var carrierCnt = 0
                            var bagCnt = 0

                            Log.d("@@@@@@@@@@@@@@@@TAG", response.body()!!.bagImgDtos.toString())

                            for (i in 0 until bagDtoList_.size) {
                                if (bagDtoList_[i].bagType == "CARRIER") {
                                    totalCnt += bagDtoList_[i].bagCount.toInt()
                                    carrierCnt = bagDtoList_[i].bagCount.toInt()
                                    tv_carrier_num_reservedetail.text = carrierCnt.toString()
                                    Log.d("@@@@@@@@@@@@@CARRIER", bagDtoList_[i].bagCount.toInt().toString())
                                } else {
                                    totalCnt += bagDtoList_[i].bagCount.toInt()
                                    bagCnt = bagDtoList_[i].bagCount.toInt()
                                    tv_bag_num_reservedetail.text = bagCnt.toString()
                                    Log.d("@@@@@@@@@@@@@@BAG", bagDtoList_[i].bagCount.toInt().toString())
                                }
                            }
                            tv_num_reservedetail.text = totalCnt.toString()

                            var priceUnit = response.body()!!.price / totalCnt

                            tv_carrier_money_reservedetail.text = (priceUnit * carrierCnt).toString()
                            tv_bag_money_reservedetail.text = (priceUnit * bagCnt).toString()

                            tv_total_num_reservedetail.text = totalCnt.toString()
                            tv_total_money_reservedetail.text = priceUnit.toString()
                            tv_payment_amount_reservedetail.text = response.body()!!.price.toString()

                            toast(response.body()!!.progressType)

                            if (response.body()!!.progressType == "DONE") {
                                iv_no_payment_reservedetail.setImageResource(R.drawable.reserve_pay_rect_gray)
                                btn_picture_reservedetail.visibility = View.INVISIBLE
                            }

                            //(
                            var allDateFormat = SimpleDateFormat("yy년 MM월 dd일 E요일")
                            var allTimeFormat = SimpleDateFormat("aa HH시 mm분")
                            var StoreDateText = allDateFormat.format(response.body()!!.startTime)
                            var StoreTimeText = allTimeFormat.format(response.body()!!.startTime)
                            var TakeDateText = allDateFormat.format(response.body()!!.endTime)
                            var TakeTimeText = allTimeFormat.format(response.body()!!.endTime)

                            tv_startday_reservedetail.text = StoreDateText
                            tv_starttime_reservedetail.text = StoreTimeText
                            tv_endday_reservedetail.text = TakeDateText
                            tv_endtime_reservedetail.text = TakeTimeText

                            //총 시간                             textView112
                            var zero = 0
//                            var allDateStamp = SimpleDateFormat("총 yy d일 HH시간 mm분")
                            var minTime = (response.body()!!.endTime - response.body()!!.startTime)
                            var allDay = minTime / 86400000
                            var allHour: Long
                            var allMinute: Long


                            if (allDay == zero.toLong()) {
                                allHour = (response.body()!!.endTime - response.body()!!.startTime) / 3600000
                                if (allHour == zero.toLong()) {
                                    allMinute = minTime / 60000
                                    tv_total_day_reservedetail.text = allMinute.toString() + "분"
                                } else {
                                    var allMinute = (minTime - (allHour * 3600000)) / 60000
                                    if (allMinute == zero.toLong()) {
                                        tv_total_day_reservedetail.text = allHour.toString() + "시간 "
                                    } else {
                                        tv_total_day_reservedetail.text = allHour.toString() + "시간 " + allMinute + "분"
                                    }
                                }
                            } else {
                                allHour = (minTime - (allDay * 86400000)) / 3600000
                                if (allHour == zero.toLong()) {
                                    allMinute = (minTime - (allDay * 86400000)) / 60000
                                    if (allMinute == zero.toLong()) {
                                        tv_total_day_reservedetail.text = allDay.toString() + "일"
                                    } else {
                                        tv_total_day_reservedetail.text = allDay.toString() + "일 " + allMinute + "분"
                                    }
                                } else {
                                    allMinute = (minTime - (allDay * 86400000) - (allHour * 3600000)) / 60000
                                    if (allMinute == zero.toLong()) {
                                        tv_total_day_reservedetail.text = allDay.toString() + "일 " + allHour + "시간 "
                                    } else {
                                        tv_total_day_reservedetail.text = allDay.toString() + "일 " + allHour + "시간 " + allMinute + "분"
                                    }
                                }
                            }

                            toast(response.body()!!.stateType)


                            Glide.with(this@ReserveDetailFragment)
                                    .load(response.body()!!.userImgUrl)
                                    .into(iv_profile_reservedetail)

                            if (response.body()!!.stateType == "RESERVED") {
                                toast("reserved")
                                layout_pickup_reservedetail.visibility = View.GONE
                                layout_storage_reservedetail.visibility = View.VISIBLE
                                layout_picture_reservedetail.visibility = View.VISIBLE
                            }
//                            else if(response.body()!!.stateType == "PAYED"){
//                                layout_pickup_reservedetail.visibility = View.INVISIBLE
//                                layout_storage_reservedetail.visibility = View.VISIBLE
//                            }
                            else if (response.body()!!.stateType == "ARCHIVE") {
                                toast("archive")
                                layout_pickup_reservedetail.visibility = View.VISIBLE
                                layout_storage_reservedetail.visibility = View.GONE
                                layout_picture_reservedetail.visibility = View.GONE



                                Log.d("@@@@@!!!@@@@", response.body()!!.bagImgDtos.toString())
                                imgUrlRVAdapter = ImgUrlRVAdapter(context,response.body()!!.bagImgDtos )
                                rv_luggage_picture.adapter = imgUrlRVAdapter
                                var mLayoutManager = LinearLayoutManager(context)
                                mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                                 rv_luggage_picture.layoutManager = mLayoutManager
                            } else {
                                toast("else")
                            }


                        }
                        400 -> {
                            toast("예약 및 보관내역 없음")
                        }
                        403 -> {
                            toast("인증 에러")
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


    private fun putStorePickupResponse() {
        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
//        bagImgRequestDtoData.bagImgUrl = imgUrl
        var vd = BagImgRequestDtoData(imgUrl)
//        var asd : BagImgRequestDtoData = imgUrl
//        asd.bagImgUrl = imgUrl
        val putStorePickUpResponse = networkService.putStorePickUpResponse(jwt, "application/json", vd, reserveIdx.toLong())

        putStorePickUpResponse!!.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("@@@@@TAG", t.message)
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            toast("짐 보관 및 픽업 성공")
                            StorageDialog(context).show()
                        }
                        401 -> {
                            toast("인증 에러")
                        }
                        500 -> {
                            toast("서버 에러")
                        }
                        else -> {
                            toast("error")
                            Log.d("!!!!!!!!!!", "!!!!!!!!!!" + it.errorBody().toString())

                        }
                    }
                }
            }

        })

    }

    fun getDetailReserveResponse() {
        var jwt = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        var getDetailReserveResponse = networkService.getDetailReserveResponse(jwt, reserveIdx.toInt())
        getDetailReserveResponse.enqueue(object : Callback<ReserveDetailResponseData> {
            override fun onFailure(call: Call<ReserveDetailResponseData>, t: Throwable) {
                toast("onFailure")
            }

            override fun onResponse(call: Call<ReserveDetailResponseData>, response: Response<ReserveDetailResponseData>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            tv_number_reservedetail.text = response.body()!!.reserveCode

                            toast("예약코드 조회 성공")
                            tv_name_reservedetail.text = response.body()!!.userName
                            tv_phone_reservedetail.text = response.body()!!.userPhone
                            if (response.body()!!.payType == "CASH") {
                                tv_payment_reservedetail.text = "현금"
                            } else {
                                tv_payment_reservedetail.text = "카카오페이"
                            }
                            var bagDtoList_: ArrayList<BagDtos> = response.body()!!.bagDtoList
                            var totalCnt = 0
                            var carrierCnt = 0
                            var bagCnt = 0

                            Log.d("@@@@@@@@@@@@@@@@TAG", bagDtoList_.toString())

                            for (i in 0 until bagDtoList_.size) {
                                if (bagDtoList_[i].bagType == "CARRIER") {
                                    totalCnt += bagDtoList_[i].bagCount.toInt()
                                    carrierCnt = bagDtoList_[i].bagCount.toInt()
                                    tv_carrier_num_reservedetail.text = carrierCnt.toString()
                                    Log.d("@@@@@@@@@@@@@CARRIER", bagDtoList_[i].bagCount.toInt().toString())
                                } else {
                                    totalCnt += bagDtoList_[i].bagCount.toInt()
                                    bagCnt = bagDtoList_[i].bagCount.toInt()
                                    tv_bag_num_reservedetail.text = bagCnt.toString()
                                    Log.d("@@@@@@@@@@@@@@BAG", bagDtoList_[i].bagCount.toInt().toString())
                                }
                            }
                            tv_num_reservedetail.text = totalCnt.toString()

                            var priceUnit = response.body()!!.price / totalCnt

                            tv_carrier_money_reservedetail.text = (priceUnit * carrierCnt).toString()
                            tv_bag_money_reservedetail.text = (priceUnit * bagCnt).toString()

                            tv_total_num_reservedetail.text = totalCnt.toString()
                            tv_total_money_reservedetail.text = priceUnit.toString()
                            tv_payment_amount_reservedetail.text = response.body()!!.price.toString()

                            toast(response.body()!!.progressType)

                            if (response.body()!!.progressType == "DONE") {
                                iv_no_payment_reservedetail.setImageResource(R.drawable.reserve_pay_rect_gray)
                                btn_picture_reservedetail.visibility = View.INVISIBLE
                            }

                            var allDateFormat = SimpleDateFormat("yy년 MM월 dd일 E요일")
                            var allTimeFormat = SimpleDateFormat("aa HH시 mm분")
                            var StoreDateText = allDateFormat.format(response.body()!!.startTime)
                            var StoreTimeText = allTimeFormat.format(response.body()!!.startTime)
                            var TakeDateText = allDateFormat.format(response.body()!!.endTime)
                            var TakeTimeText = allTimeFormat.format(response.body()!!.endTime)

                            tv_startday_reservedetail.text = StoreDateText
                            tv_starttime_reservedetail.text = StoreTimeText
                            tv_endday_reservedetail.text = TakeDateText
                            tv_endtime_reservedetail.text = TakeTimeText

                            //총 시간                             textView112
                            var zero = 0
//                            var allDateStamp = SimpleDateFormat("총 yy d일 HH시간 mm분")
                            var minTime = (response.body()!!.endTime - response.body()!!.startTime)
                            var allDay = minTime / 86400000
                            var allHour: Long
                            var allMinute: Long


                            if (allDay == zero.toLong()) {
                                allHour = (response.body()!!.endTime - response.body()!!.startTime) / 3600000
                                if (allHour == zero.toLong()) {
                                    allMinute = minTime / 60000
                                    tv_total_day_reservedetail.text = allMinute.toString() + "분"
                                } else {
                                    var allMinute = (minTime - (allHour * 3600000)) / 60000
                                    if (allMinute == zero.toLong()) {
                                        tv_total_day_reservedetail.text = allHour.toString() + "시간 "
                                    } else {
                                        tv_total_day_reservedetail.text = allHour.toString() + "시간 " + allMinute + "분"
                                    }
                                }
                            } else {
                                allHour = (minTime - (allDay * 86400000)) / 3600000
                                if (allHour == zero.toLong()) {
                                    allMinute = (minTime - (allDay * 86400000)) / 60000
                                    if (allMinute == zero.toLong()) {
                                        tv_total_day_reservedetail.text = allDay.toString() + "일"
                                    } else {
                                        tv_total_day_reservedetail.text = allDay.toString() + "일 " + allMinute + "분"
                                    }
                                } else {
                                    allMinute = (minTime - (allDay * 86400000) - (allHour * 3600000)) / 60000
                                    if (allMinute == zero.toLong()) {
                                        tv_total_day_reservedetail.text = allDay.toString() + "일 " + allHour + "시간 "
                                    } else {
                                        tv_total_day_reservedetail.text = allDay.toString() + "일 " + allHour + "시간 " + allMinute + "분"
                                    }
                                }
                            }

                            val requestOptions = RequestOptions()
                            requestOptions.placeholder(R.drawable.mypage_bt_default)
                            requestOptions.error(R.drawable.mypage_bt_default)


                            Glide.with(this@ReserveDetailFragment)
                                    .setDefaultRequestOptions(requestOptions)
                                    .load(response.body()!!.userImgUrl)
                                    .into(iv_profile_reservedetail)


                            //짐 보관, 픽업
                            pick_reserveIdx = response.body()!!.reserveIdx

                            toast(response.body()!!.stateType)
                            if (response.body()!!.stateType == "RESERVED") {   //예약상태
                                layout_storage_reservedetail.visibility = View.VISIBLE
                                layout_pickup_reservedetail.visibility = View.GONE

                                btn_storage_reservedetail.setOnClickListener {
                                    if (cb_confirm_reservedetail.isChecked) {
                                        putStorePickupResponse()
//                                        StorageDialog(context).show()
//                                        (ctx as MainActivity).replaceFragment(ReserveDetailFragment())
                                    } else {
                                        toast("동의해주세요")
                                    }
                                }

                            } else if (response.body()!!.stateType == "ARCHIVE") {   //보관상태
                                layout_storage_reservedetail.visibility = View.GONE
                                layout_pickup_reservedetail.visibility = View.VISIBLE
                                layout_picture_reservedetail.visibility = View.GONE

                                bagImgDtos = response.body()!!.bagImgDtos

                                imgUrlRVAdapter = ImgUrlRVAdapter(context,response.body()!!.bagImgDtos )
                                rv_luggage_picture.adapter = imgUrlRVAdapter
                                var mLayoutManager = LinearLayoutManager(context)
                                mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                                rv_luggage_picture.layoutManager = mLayoutManager

//                                PickUpDialog(context).show()
//                                (ctx as MainActivity).replaceFragment(ReserveDetailFragment())
//                            }else if(response.body()!!.stateType == "PICKUP"){
//                                layout_storage_reservedetail.visibility = View.GONE
//                                layout_pickup_reservedetail.visibility = View.VISIBLE
                            } else {

                            }


                        }
                        403 -> {
                            toast("인증에러")
                        }
                        500 -> {
                            toast("서버에러")
                        }
                        else -> {
                            toast("Else Error")
                        }
                    }
                }
            }
        })

    }
}