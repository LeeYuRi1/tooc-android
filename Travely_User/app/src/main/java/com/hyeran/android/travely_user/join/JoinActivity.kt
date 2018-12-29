package com.hyeran.android.travely_user.join

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hyeran.android.travely_user.MainActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.activity_join.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern

class JoinActivity : AppCompatActivity() {

    lateinit var networkService : NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        init()
    }

    private fun init() {
        networkService = ApplicationController.instance.networkService
        setOnClickListener()
        checkValidation()
    }

    private fun setOnClickListener() {
        btn_confirm_join.setOnClickListener {
            postJoinResponse()
        }
    }

    // 유효성 검사
    private fun checkValidation() {

        var name_validation = false
        var email_validation = false
        var pw_validation = false
        var pw_confirm_validation = false

        // 이름: 공백인지 아닌지
        et_name_join.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                name_validation = et_name_join.text.toString().trim().isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 이메일: 이메일 형식인지 아닌지
        et_email_join.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (checkEmail(et_email_join.text.toString().trim())) {
                    iv_email_check_join.visibility = View.VISIBLE
                    email_validation = true
                }
                else {
                    iv_email_check_join.visibility = View.INVISIBLE
                    email_validation = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 패스워드: 8-20자, 영어+번호+특수문자
        et_password_join.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (et_password_join.text.toString().length != 0) {
                    iv_password_check_join.visibility = View.VISIBLE
                } else if (et_password_join.text.toString().length == 0) {
                    iv_password_check_join.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 패스워드 확인
        et_password_confilm_join.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (et_password_confilm_join.text.toString().length != 0) {
                    if (et_password_join.text.toString() == et_password_confilm_join.text.toString()) {
                        iv_passwordconfirm_check_join.visibility = View.VISIBLE
                        iv_passwordconfirm_x_join.visibility = View.GONE
                    } else {
                        iv_passwordconfirm_check_join.visibility = View.GONE
                        iv_passwordconfirm_x_join.visibility = View.VISIBLE
                    }
                } else if (et_password_confilm_join.text.toString().length == 0) {
                    iv_passwordconfirm_check_join.visibility = View.GONE
                    iv_passwordconfirm_x_join.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun checkEmail(email : String) : Boolean{
        val regex : String = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"
        val p : Pattern = Pattern.compile(regex)
        val m : Matcher = p.matcher(email)
        val isNormal : Boolean = m.matches()
        return isNormal
    }

    private fun postJoinResponse() {
        val input_email = et_email_join.text.toString().trim()
        val input_pw = et_password_join.text.toString().trim()
        val input_config_pw = et_password_confilm_join.text.toString().trim()
        val input_name = et_name_join.text.toString().trim()
        val input_phone = et_phone_join.text.toString().trim()

        var jsonObject = JSONObject()
        jsonObject.put("email", input_email)
        jsonObject.put("password", input_pw)
        jsonObject.put("configPassword", input_config_pw)
        jsonObject.put("name", input_name)
        jsonObject.put("phone", input_phone)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postJoinResponse = networkService.postJoinResponse(gsonObject)

        postJoinResponse!!.enqueue(object : Callback<Any>{
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("Error JoinActivity : ", t.message)
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response?.let {
                    when (it.code()) {
                        201 -> {
                            toast("유저 생성 성공")
                            SharedPreferencesController.instance!!.setPrefData("jwt", response.headers().value(0))
                            SharedPreferencesController.instance!!.setPrefData("auto_login", true)
                            SharedPreferencesController.instance!!.setPrefData("user_email", input_email)
                            SharedPreferencesController.instance!!.setPrefData("user_pw", input_pw)
                            startActivity(Intent(this@JoinActivity, MainActivity::class.java))
                            finish()
                        }
                        400 -> {
                            toast("유효성 체크 에러 혹은 중복된 이메일")
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
}