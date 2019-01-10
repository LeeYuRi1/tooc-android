package com.hyeran.android.travely_manager.network

import com.google.gson.JsonObject
import com.hyeran.android.travely_manager.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    // 로그인
    // - 성공 시 jwt 토큰을 헤더에 넣어서 반환
    @POST("/api/users/login")
    fun postLoginResponse(
            @Header("Content-Type") content_type: String,
            @Body() body: JsonObject
    ): Call<Any>

    // ownerIdx를 기반으로 storeIdx를 반환
    @GET("/api/owner/store")
    fun getStoreIdxResponse(
            @Header("jwt") jwt: String?
    ): Call<StoreIdxData>

    // 관리자 마이페이지
    @GET("/api/owner/mypage")
    fun getMypageResponse(
            @Header("jwt") jwt: String?
    ): Call<MypageResponseData>

    // 상가의 예약 On/Off 기능 반전
    @PUT("/api/owner/mypage/onoff")
    fun putMypageOnOffResponse(
            @Header("jwt") jwt: String?
    ): Call<Any>

    // 가게 예약 보관 목록 조회
    @GET("/api/owner/reserve")
    fun getReserveResponse(
            @Header("jwt") jwt: String?
    ): Call<ReserveListResponseData>

    // 보관 내역 상세 조회
    @GET("/api/owner/reserve/{reserveIdx}")
    fun getDetailReserveResponse(
            @Header("jwt") jwt: String?,
            @Path("reserveIdx") reserveIdx: Int
    ): Call<ReserveDetailResponseData>

    // 짐 보관 및 픽업
    @PUT("/api/owner/reserve/{reserveIdx}")
    fun putStorePickUpResponse(
            @Header("jwt") jwt: String?,
            @Path("reserveIdx") reserveIdx: Int
    ): Call<Any>

    // 예약코드 조회
    @GET("/api/owner/reserve/{storeIdx}/{reserveCode}")
    fun getStoreIdxReserveCodeResponse(
            @Header("jwt") jwt: String?,
            @Path("reserveCode") reserveCode: String,
            @Path("storeIdx") storeIdx: Int
    ): Call<ReserveDetailResponseData>

    // 가게 리뷰 보기
    @GET("/api/owner/review")
    fun getReviewResponse(
            @Header("jwt") jwt: String?
    ): Call<ReviewResponseData>

    // 가게 리뷰 추가 보기
    @GET("/api/owner/review/{reviewIdx}")
    fun getReviewMoreResponse(
            @Header("jwt") jwt: String?,
            @Path("reviewIdx") reviewIdx: Int
    ): Call<ArrayList<ReviewMoreResponseData>>

    // 사진 등록
    @Multipart
    @POST("/api/img")
    fun postImgResponse(
            @Header("jwt") jwt: String?,
            @Part data: MultipartBody.Part
    ): Call<BagImgDtos>
}