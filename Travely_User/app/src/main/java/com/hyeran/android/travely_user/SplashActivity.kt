package com.hyeran.android.travely_user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hyeran.android.travely_user.join.LoginActivity
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashActivity : AppCompatActivity() {

    lateinit var networkService : NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        init()
    }

    private fun init() {
        // 초기 화면에 SharedPreferenceContorller의 pref 활성화
        SharedPreferencesController.instance!!.load(this)

        networkService = ApplicationController.instance.networkService

        // 3초 뒤 MainActivity로 이동
        moveActivity()
    }

    private fun moveActivity() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            val auto_login_flag = SharedPreferencesController.instance!!.getPrefBooleanData("auto_login")
            // true: 자동 로그인 O, false: 자동 로그인 X
            val intent : Intent
            if (auto_login_flag) {
                postLoinResponse()  // 자동 로그인 시 토큰 값 받아오기 위한 통신
                intent = Intent(applicationContext, MainActivity::class.java)
            }
            else {
                intent = Intent(applicationContext, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 3000)
    }

    private fun postLoinResponse() {
        val user_email = SharedPreferencesController.instance!!.getPrefStringData("user_email")
        val user_pw = SharedPreferencesController.instance!!.getPrefStringData("user_pw")

        var jsonObject = JSONObject()
        jsonObject.put("email", user_email!!.trim())
        jsonObject.put("password", user_pw!!.trim())

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postLoginResponse = networkService.postLoginResponse("application/json", gsonObject)

        postLoginResponse!!.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            toast("로그인 성공")
                            SharedPreferencesController.instance!!.setPrefData("jwt", response.headers().value(0))
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