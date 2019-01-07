package com.hyeran.android.travely_manager.network

import com.hyeran.android.travely_manager.model.MypageResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface NetworkService{
    // 관리자 마이페이지
    @GET("/api/owner/mypage")
    fun getMypageResponse(
            @Header("jwt") jwt: String?
    ): Call<MypageResponseData>
}