package com.tooc.android.tooc.mypage

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tooc.android.tooc.MainActivity
import com.tooc.android.tooc.R
import com.tooc.android.tooc.adapter.QuestionAdapter
import com.tooc.android.tooc.model.QuestionData
import com.tooc.android.tooc.model.BagImgDtos
import com.tooc.android.tooc.model.mypage.InquiryResponseData
import com.tooc.android.tooc.network.ApplicationController
import com.tooc.android.tooc.network.NetworkService
import kotlinx.android.synthetic.main.fragment_question.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class QuestionFragment : Fragment() {

    lateinit var questionAdapter: QuestionAdapter

    lateinit var networkService: NetworkService

    val REQUEST_CODE_SELECT_IMAGE: Int = 1004

    private var inquiryImgs: MultipartBody.Part? = null

    var inquiryImageSave: ArrayList<String> = ArrayList()

    val dataList: ArrayList<QuestionData> by lazy {
        ArrayList<QuestionData>()
    }

    lateinit var filename : String

    var imgUrl = ArrayList<String>()
    lateinit var bagImgDtos : ArrayList<BagImgDtos>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_question, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        networkService = ApplicationController.instance.networkService

        setRecyclerView()
        setClickListener()


    }

    private fun setRecyclerView() {
        questionAdapter = QuestionAdapter(activity!!, dataList)
        rv_file_question.adapter = questionAdapter
        rv_file_question.layoutManager = LinearLayoutManager(activity)


    }

    private fun setClickListener() {
        iv_back_question.setOnClickListener {
            (ctx as MainActivity).replaceFragment(SetFragment())
        }

        layout_add_question.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
            intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
//            var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(takePictureIntent, REQUEST_CODE_SELECT_IMAGE)
        }

        btn_ok_question.setOnClickListener {
            postInquiryResponse()
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    var seletedPictureUri = it.data
                    val options = BitmapFactory.Options()
                    val inputStream: InputStream = ctx.contentResolver.openInputStream(seletedPictureUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray())
                    //첫번째 매개변수 String을 꼭! 꼭! 서버 API에 명시된 이름으로 넣어주세요!!!
                    inquiryImgs = MultipartBody.Part.createFormData("inquiryImgs", File(seletedPictureUri.toString()).name, photoBody)

                    filename = File(seletedPictureUri.toString()).name.toString()

                    try {
                        if (inquiryImgs != null) {
                            dataList.add(QuestionData(filename))   //리사이클러뷰 추가
                            //inquiryImageSave.add(inquiryImgs.toString())   //서버에 넘기는 이미지 추가
                            inquiryImageSave.add(filename)
                        }

                        questionAdapter = QuestionAdapter(activity!!, dataList)
                        rv_file_question.adapter = questionAdapter
                        rv_file_question.layoutManager = LinearLayoutManager(activity)

                    } catch (e: Exception) {
                        Log.e("test", e.message)
                    }
                }
            }
        }

//        if (requestCode === REQUEST_CODE_SELECT_IMAGE && resultCode === Activity.RESULT_OK) {
//
//            if (data != null) {
//
//                val extras = data!!.getExtras()
//                val imageBitmap = extras.get("data") as Bitmap
//
//                val bmp: Bitmap? = imageBitmap
//                val stream = ByteArrayOutputStream()
//                bmp!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//                val byteArray = stream.toByteArray()
//
//                var f: File = File(context!!.cacheDir, "tooc.jpg")
//                f.createNewFile()
//
//                var fos: FileOutputStream = FileOutputStream(f)
//                fos.write(byteArray)
//                fos.flush()
//                fos.close()
//
//                var file = context!!.cacheDir.toString() + "tooc"
//                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
//
//                val photoBody = RequestBody.create(MediaType.parse("image/jpg"), byteArray)
//                //첫번째 매개변수 String을 꼭! 꼭! 서버 API에 명시된 이름으로 넣어주세요!!!
//                var mImage = MultipartBody.Part.createFormData("data", f.name, photoBody);
//                //postImgResponse(mImage)
//
//
//                Log.d("fffffffffffffffffffff", "사진 이름: "+f.name.toString())
//                dataList.add(QuestionData(f.name.toString()))
//
//                questionAdapter = QuestionAdapter(activity!!, dataList)
//                rv_file_question.adapter = questionAdapter
//                rv_file_question.layoutManager = LinearLayoutManager(activity)
//
//
//            } else {
//                toast("기본 카메라로 설정해주세요.")
//            }
//        }
    }


    private fun postInquiryResponse() {
        var content = et_content_question.text.toString()

        var inquirySave: InquiryResponseData

        inquirySave = InquiryResponseData(content, inquiryImageSave)   //잘못했음 다시해야돼. 이미지 이름 보내짐

        Log.d("tttttttt", "사진: "+inquirySave.toString())

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")
        val postInquiryResponse = networkService.postInquiryResponse("application/json", jwt, inquirySave)

        postInquiryResponse!!.enqueue(object : Callback<InquiryResponseData> {
            override fun onFailure(call: Call<InquiryResponseData>, t: Throwable) {
            }

            override fun onResponse(call: Call<InquiryResponseData>, response: Response<InquiryResponseData>) {
                response?.let {
                    when (it.code()) {
                        200 -> {

                            content = et_content_question.text.toString()

                            if (content.isNotEmpty()) {
                                (ctx as MainActivity).replaceFragment(SetFragment())
                                toast("문의사항 작성 성공")

                            } else {
                                toast("문의사항을 작성해주세요")
                            }

                        }


                        500 -> {
                            //toast("서버 에러")
                            (ctx as MainActivity).replaceFragment(SetFragment())
                        }
                        else -> {
                            //toast("error")
                            (ctx as MainActivity).replaceFragment(SetFragment())
                        }
                    }
                }
            }

        })
    }

}