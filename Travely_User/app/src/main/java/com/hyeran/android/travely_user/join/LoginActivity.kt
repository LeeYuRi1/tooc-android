package com.hyeran.android.travely_user.join

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.model.reservation.UsersLoginResponseData
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var networkService : NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        setClickListener()
    }

    fun init() {
        networkService = ApplicationController.instance.networkService
    }

    private fun setClickListener() {
        tv_join_login.setOnClickListener {
            val intent = Intent(applicationContext, JoinActivity::class.java)
            startActivity(intent)
        }

        btn_login_login.setOnClickListener {
            postLoinResponse()
        }
    }

    private fun postLoinResponse() {
        val input_email = et_email_login.text.toString().trim()
        val input_pw = et_password_login.text.toString().trim()

        var jsonObject = JSONObject()
        jsonObject.put("email", input_email)
        jsonObject.put("password", input_pw)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postLoginResponse = networkService.postLoginResponse("application/json", gsonObject)

        postLoginResponse!!.enqueue(object : Callback<UsersLoginResponseData> {
            override fun onFailure(call: Call<UsersLoginResponseData>, t: Throwable) {
            }
            override fun onResponse(call: Call<UsersLoginResponseData>, response: Response<UsersLoginResponseData>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            toast("로그인 성공")
                            SharedPreferencesController.instance!!.setPrefData("jwt", response.headers().value(0))
                            SharedPreferencesController.instance!!.setPrefData("auto_login", true)
                            SharedPreferencesController.instance!!.setPrefData("user_email", input_email)
                            SharedPreferencesController.instance!!.setPrefData("user_pw", input_pw)
                            SharedPreferencesController.instance!!.setPrefData("is_reserve", response.body()!!.isReserve)
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                        403 -> {
                            toast("로그인 실패")
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
