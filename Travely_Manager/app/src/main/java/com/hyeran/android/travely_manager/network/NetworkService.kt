package com.hyeran.android.travely_manager.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface NetworkService{
//@@@@@@@@@@@@@@@ user-controller @@@@@@@@@@@@@@@

    // 로그인
    // - 성공 시 jwt 토큰을 헤더에 넣어서 반환
    @POST("/api/users/login")
    fun postLoginResponse(
            @Header("Content-Type") content_type: String,
            @Body() body: JsonObject
    ): Call<Any>
}