package com.hyeran.android.travely_user.join

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.R
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

        SharedPreferencesController.instance!!.load(this)

        setClickListener()
    }

    fun init() {
//        signUpCompleteBtn.setOnClickListener(this)
//        signUpXBtn.setOnClickListener(this)
        networkService = ApplicationController.instance.networkService
//        SharedPreference.instance!!.load(this)
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
        val input_email = et_email_login.text.toString()
        val input_pw = et_password_login.text.toString()

        var jsonObject = JSONObject()
        jsonObject.put("email", input_email.trim())
        jsonObject.put("password", input_pw.trim())

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postLoginResponse = networkService.postLoginResponse("application/json", gsonObject)

        postLoginResponse!!.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            SharedPreferencesController.instance!!.setPrefData("jwt", response.headers().value(0))
//                            Log.v("“wo:“, response.message().toString())
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                        403 -> {
                            toast("로그인 실패")
                        }

                        400 -> {
                            toast("요청 실패")
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
