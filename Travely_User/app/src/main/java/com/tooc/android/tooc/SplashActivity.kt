package com.tooc.android.tooc

import android.animation.Animator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.tooc.android.tooc.join.LoginActivity
import com.tooc.android.tooc.model.reservation.UsersLoginResponseData
import com.tooc.android.tooc.network.ApplicationController
import com.tooc.android.tooc.network.NetworkService
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    var networkService : NetworkService = ApplicationController.instance.networkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init() {
        SharedPreferencesController.instance!!.load(this) // 최초 화면에서 SharedPreferenceController 활성화
        lottieAnimControl()
    }

    private fun lottieAnimControl() {
        val lottie : LottieAnimationView = findViewById(R.id.lottie_splash)
        lottie.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationEnd(animation: Animator?) {
                val autoLoginFlag = SharedPreferencesController.instance!!.getPrefBooleanData("auto_login")
                if (autoLoginFlag) postLoinResponse() // 자동 로그인 시 토큰 값 받아오기 위한 통신
                else startActivity<LoginActivity>()
                finish()
            }
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
    }

    private fun postLoinResponse() {
        val userEmail = SharedPreferencesController.instance!!.getPrefStringData("user_email")
        val userPW = SharedPreferencesController.instance!!.getPrefStringData("user_pw")

        var jsonObject = JSONObject()
        jsonObject.put("email", userEmail!!)
        jsonObject.put("password", userPW!!)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postLoginResponse = networkService.postLoginResponse("application/json", gsonObject)

        postLoginResponse.enqueue(object : Callback<UsersLoginResponseData> {
            override fun onFailure(call: Call<UsersLoginResponseData>, t: Throwable) {
                Log.d("Log::SplashActivity65", t.message)
            }

            override fun onResponse(call: Call<UsersLoginResponseData>, response: Response<UsersLoginResponseData>) {
                response.let {
                    when (it.code()) {
                        200 -> {
                            SharedPreferencesController.instance!!.setPrefData("jwt", response.headers().value(0))
                            SharedPreferencesController.instance!!.setPrefData("is_reserve", response.body()!!.isReserve)
                            startActivity<MainActivity>()
                        }
                        403 -> toast("로그인 실패")
                        500 -> toast("서버 에러")
                        else -> {
                            toast("error")
                            Log.d("Log::SplashActivity80", it.code().toString())
                        }
                    }
                }
            }
        })
    }
}