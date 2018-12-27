package com.hyeran.android.travely_user.join

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.R
import com.hyeran.android.travely_user.network.ApplicationController
import com.hyeran.android.travely_user.network.NetworkService
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {

    lateinit var networkService : NetworkService

    fun init() {
//        signUpCompleteBtn.setOnClickListener(this)
//        signUpXBtn.setOnClickListener(this)
        networkService = ApplicationController.instance.networkService
//        SharedPreference.instance!!.load(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        init()
        setClickListener()
    }

    private fun setClickListener() {
        btn_confirm_join.setOnClickListener {
            postJoinResponse()
            val intent = Intent(applicationContext, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun postJoinResponse() {
        val input_email = et_email_join.text.toString()
        val input_pw = et_password_join.text.toString()
        val input_config_pw = et_password_join.text.toString()
        val input_name = et_name_join.text.toString()
        val input_phone = et_phone_join.text.toString()

        var jsonObject = JSONObject()
        jsonObject.put("email", input_email.trim())
        jsonObject.put("password", input_pw.trim())
        jsonObject.put("configPassword", input_config_pw.trim())
        jsonObject.put("name", input_name.trim())
        jsonObject.put("phone", input_phone.trim())

        Log.d("jsonObject", "@@@@@@@"+jsonObject)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postJoinResponse = networkService.postJoinResponse(gsonObject)

        postJoinResponse!!.enqueue(object : Callback<Any>{
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("Error LoginActivity : ", t.message)
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                response?.let {
                    when (it.code()) {
                        201 -> {
                            Log.d("Join", "@@@@@@"+response.headers().value(0))
                            SharedPreferencesController.instance!!.setPrefData("jwt", response.headers().value(0))
//                            Log.v("“wo:“, response.message().toString())
                            startActivity(Intent(this@JoinActivity, MainActivity::class.java))
                            finish()
                        }
                        400 -> {
                            toast("중복된 이메일입니다.")
                        }
                        401 -> {
                        }
                        500 -> {
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
