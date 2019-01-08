package com.hyeran.android.travely_manager.network

import com.google.gson.JsonObject
import com.hyeran.android.travely_manager.model.MypageResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NetworkService{

    // 로그인
    // - 성공 시 jwt 토큰을 헤더에 넣어서 반환
    @POST("/api/users/login")
    fun postLoginResponse(
            @Header("Content-Type") content_type: String,
            @Body() body: JsonObject
    ): Call<Any>

    // 관리자 마이페이지
    @GET("/api/owner/mypage")
    fun getMypageResponse(
            @Header("jwt") jwt: String?
    ): Call<MypageResponseData>
}