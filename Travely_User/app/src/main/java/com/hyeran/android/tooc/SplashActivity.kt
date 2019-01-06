package com.hyeran.android.tooc

import android.animation.Animator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hyeran.android.tooc.join.LoginActivity
import com.hyeran.android.tooc.model.reservation.UsersLoginResponseData
import com.hyeran.android.tooc.network.ApplicationController
import com.hyeran.android.tooc.network.NetworkService
import kotlinx.android.synthetic.main.activity_splash.*
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

        val lottie_splash : LottieAnimationView = findViewById(R.id.lottie_splash)
        lottie_splash.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
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
            }

        })

        init()
    }

    private fun init() {
        // 초기 화면에 SharedPreferenceContorller의 pref 활성화
        SharedPreferencesController.instance!!.load(this)

        networkService = ApplicationController.instance.networkService

        // 3초 뒤 MainActivity로 이동
//        moveActivity()
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
        }, 1000)
    }

    private fun postLoinResponse() {
        val user_email = SharedPreferencesController.instance!!.getPrefStringData("user_email")
        val user_pw = SharedPreferencesController.instance!!.getPrefStringData("user_pw")

        var jsonObject = JSONObject()
        jsonObject.put("email", user_email!!.trim())
        jsonObject.put("password", user_pw!!.trim())

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
                            SharedPreferencesController.instance!!.setPrefData("is_reserve", response.body()!!.isReserve)
//                            toast(SharedPreferencesController.instance!!.getPrefBooleanData("is_reserve").toString())
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