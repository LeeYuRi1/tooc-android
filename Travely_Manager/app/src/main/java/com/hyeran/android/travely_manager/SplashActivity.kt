package com.hyeran.android.travely_manager

import android.animation.Animator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hyeran.android.travely_manager.db.SharedPreferencesController
import com.hyeran.android.travely_manager.login.LoginActivity
import com.hyeran.android.travely_manager.network.ApplicationController
import com.hyeran.android.travely_manager.network.NetworkService
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashActivity : AppCompatActivity() {
    lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val lottieSplash: LottieAnimationView = findViewById(R.id.lottie_splash)
        lottieSplash.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                var autoLoginFlag = SharedPreferencesController.instance!!.getPrefBooleanData("auto_login")
                val intent: Intent
                if (autoLoginFlag) {
                    postLoginResponse()
                    intent = Intent(applicationContext,MainActivity::class.java)
                } else {
                    intent = Intent(applicationContext,LoginActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
            override fun onAnimationEnd(p0: Animator?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onAnimationCancel(p0: Animator?) {
            }
            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
            }
            override fun onAnimationStart(p0: Animator?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        init()

    }

    fun init() {
        SharedPreferencesController.instance!!.load(this)
        networkService = ApplicationController.instance.networkService
    }

    fun postLoginResponse() {
        var userEmail = SharedPreferencesController.instance!!.getPrefStringData("user_email")
        var userPassword = SharedPreferencesController.instance!!.getPrefStringData("user_pw")

        var jsonObject = JSONObject()
        jsonObject.put("email",userEmail!!.trim())
        jsonObject.put("password",userPassword!!.trim())
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postLoginResponse = networkService.postLoginResponse("application/json",gsonObject)

        postLoginResponse!!.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                toast("onFail")
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response?.let {
                    when(it.code()){
                        200->{
                            toast("로그인 성공")
                        }
                        403->{
                            toast("로그인 실패")
                        }
                        500->{
                            toast("서버 에러")
                        }
                        else-> toast("else 에러")
                    }
                }
            }
        })
    }
}
