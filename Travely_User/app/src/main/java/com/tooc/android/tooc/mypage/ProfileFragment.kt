package com.tooc.android.tooc.mypage

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tooc.android.tooc.MainActivity
import com.tooc.android.tooc.R
import com.tooc.android.tooc.SplashActivity
import com.tooc.android.tooc.model.mypage.ProfileResponseData
import com.tooc.android.tooc.network.ApplicationController
import com.tooc.android.tooc.network.NetworkService
import kotlinx.android.synthetic.main.fragment_profile.*

import java.util.regex.Pattern
import okhttp3.MultipartBody
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher

class ProfileFragment : Fragment() {

    lateinit var networkService: NetworkService
    private var mImage: MultipartBody.Part? = null

    val REQUEST_CODE_SELECT_IMAGE: Int = 1004

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()
        checkValidation()
        init()
        getProfileResponse()
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

        iv_back_profile.setOnClickListener {
            var fm = fragmentManager
            (ctx as MainActivity).replaceFragment(MypageFragment())
            fm!!.popBackStack()
        }


        tv_modification_profile.setOnClickListener {

            if (tv_modification_profile.text == "수정") {   //edittext 막기
                tv_modification_profile.setText("완료")

                et_name_profile.isEnabled = true
                //et_email_profile.isEnabled = true
                et_password_profile.isEnabled = true
                et_password_confirm_profile.isEnabled = true
                et_phone_profile.isEnabled = true

            } else if (tv_modification_profile.text == "완료") {   //edittext 수정 가능
                tv_modification_profile.text = "수정"
                et_name_profile.isEnabled = false

                et_name_profile.isEnabled = false
                //et_email_profile.isEnabled = false
                et_password_profile.isEnabled = false
                et_password_confirm_profile.isEnabled = false
                et_phone_profile.isEnabled = false

                //val p_name = et_name_profile.text
            }
        }

        iv_photo_modify_profile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
            intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
        }

    }


    // 유효성 검사
    private fun checkValidation() {

        var name_validation = false
        var email_validation = false
        var pw_validation = false
        var pw_confirm_validation = false

        // 이름: 공백인지 아닌지
        et_name_profile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                name_validation = et_name_profile.text.toString().trim().isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 이메일: 이메일 형식인지 아닌지
        et_email_profile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (checkEmail(et_email_profile.text.toString().trim())) {
                    iv_email_check_profile.visibility = View.VISIBLE
                    email_validation = true
                } else {
                    iv_email_check_profile.visibility = View.INVISIBLE
                    email_validation = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 패스워드: 8-20자, 영어+번호+특수문자
        et_password_profile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (et_password_profile.text.toString().isNotEmpty()) {
                    iv_password_check_profile.visibility = View.VISIBLE
                } else if (et_password_profile.text.toString().isEmpty()) {
                    iv_password_check_profile.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 패스워드 확인
        et_password_confirm_profile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (et_password_confirm_profile.text.toString().isNotEmpty()) {
                    if (et_password_profile.text.toString() == et_password_confirm_profile.text.toString()) {
                        iv_passwordconfirm_check_profile.visibility = View.VISIBLE
                        iv_passwordconfirm_x_profile.visibility = View.GONE
                    } else {
                        iv_passwordconfirm_check_profile.visibility = View.GONE
                        iv_passwordconfirm_x_profile.visibility = View.VISIBLE
                    }
                } else if (et_password_confirm_profile.text.toString().isEmpty()) {
                    iv_passwordconfirm_check_profile.visibility = View.GONE
                    iv_passwordconfirm_x_profile.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun checkEmail(email: String): Boolean {
        val regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"
        val p: Pattern = Pattern.compile(regex)
        val m: Matcher = p.matcher(email)
        val isNormal: Boolean = m.matches()
        return isNormal
    }


    private fun getProfileResponse() {

        var jwt: String? = SharedPreferencesController.instance!!.getPrefStringData("jwt")

        val getProfileResponse = networkService.getProfileResponse(jwt)

        getProfileResponse!!.enqueue(object : Callback<ProfileResponseData> {
            override fun onFailure(call: Call<ProfileResponseData>, t: Throwable) {
            }

            override fun onResponse(call: Call<ProfileResponseData>, response: Response<ProfileResponseData>) {
                response?.let {
                    when (it.code()) {
                        200 -> {

                            val requestOptions = RequestOptions()
                            requestOptions.placeholder(R.drawable.mypage_bt_default)
                            requestOptions.error(R.drawable.mypage_bt_default)

                            Glide.with(this@ProfileFragment)
                                    .setDefaultRequestOptions(requestOptions)
                                    .load(response.body()!!.profileImg)
                                    .into(iv_profileimage_profile)

                            et_name_profile.setText(response.body()!!.name)
                            et_email_profile.setText(response.body()!!.email)
                            et_phone_profile.setText(response.body()!!.phone)

                        }
                        500 -> {
                        }
                        else -> {
                        }
                    }
                }
            }

        })
    }


}