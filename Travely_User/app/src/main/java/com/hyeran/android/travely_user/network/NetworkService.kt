package com.hyeran.android.travely_user.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface NetworkService {

    // 회원가입
    @Headers("Content-Type: application/json")
    @POST("/api/users")
    fun postJoinResponse(
            @Body() body : JsonObject
    ) : Call<Any>

    // 로그인
    @POST("/api/users/login")
    fun postLoginResponse(
            @Header("Content-Type") content_type : String,
            @Body() body : JsonObject
    ) : Call<Any>

}