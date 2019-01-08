package com.hyeran.android.travely_manager.network

import com.google.gson.JsonObject
import com.hyeran.android.travely_manager.model.MypageResponseData
import retrofit2.Call
import retrofit2.http.*

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

    // 상가의 예약 On/Off 기능 반전
    @PUT("/api/owner/mypage/onoff")
    fun putMypageOnOffResponse(
            @Header("jwt") jwt: String?
    ) : Call<Any>
}