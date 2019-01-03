package com.hyeran.android.travely_user.mypage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.SplashActivity
import com.hyeran.android.travely_user.model.ProfileResponseData
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class ProfileFragment : Fragment() {

    lateinit var networkService: NetworkService
    private var mImage: MultipartBody.Part? = null

    val REQUEST_CODE_SELECT_IMAGE : Int = 1004

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()
        init()
//        getProfileResponse()
    }

    private fun init() {
        networkService = ApplicationController.instance.networkService
    }

    private fun setClickListener() {
        btn_logout_profile.setOnClickListener {
            SharedPreferencesController.instance!!.removeAllData(this!!.context!!)

            val intent = Intent(activity, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
        }

        tv_modification_profile.setOnClickListener {

            if(tv_modification_profile.text == "수정") {
                et_name_profile.isFocusableInTouchMode = true
                et_email_profile.isFocusableInTouchMode = true
                et_password_profile.isFocusableInTouchMode = true
                et_password_confirm_profile.isFocusableInTouchMode = true

                tv_modification_profile.setText("완료")
            }else if(tv_modification_profile.text == "완료") {
                et_name_profile.isFocusableInTouchMode = false
                et_email_profile.isFocusableInTouchMode = false
                et_password_profile.isFocusableInTouchMode = false
                et_password_confirm_profile.isFocusableInTouchMode = false

                tv_modification_profile.setText("수정")

            }
        }

        iv_photo_modify_profile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
            intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
        }
    }

}