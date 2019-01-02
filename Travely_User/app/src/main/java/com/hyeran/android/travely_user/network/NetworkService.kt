package com.hyeran.android.travely_user.network

import com.google.gson.JsonObject
import com.hyeran.android.travely_user.model.RegionResponseData
import com.hyeran.android.travely_user.model.ReservationSaveResponseData
import com.hyeran.android.travely_user.model.StoreResponseData
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    //@@@@@@@@@@@@@@@ user-controller @@@@@@@@@@@@@@@

    // 일반 유저 생성
    // - 성공 시 jwt 토큰을 헤더에 넣어서 반환
    @Headers("Content-Type: application/json")
    @POST("/api/users")
    fun postJoinResponse(
            @Body() body : JsonObject
    ) : Call<Any>

    // 로그인
    // - 성공 시 jwt 토큰을 헤더에 넣어서 반환
    @POST("/api/users/login")
    fun postLoginResponse(
            @Header("Content-Type") content_type : String,
            @Body() body : JsonObject
    ) : Call<Any>

    //@@@@@@@@@@@@@@@ region-controller @@@@@@@@@@@@@@@

    // 제휴상가 지역별 목록 조회
    // - 지역명과 상가수 반환
    @GET("/api/region")
    fun getRegionResponse(
            @Header("jwt") jwt : String?
    ) : Call<ArrayList<RegionResponseData>>


    //@@@@@@@@@@@@@@@ reservation-controller @@@@@@@@@@@@@@@
    // 예약 상태 저장
    // - 예약 상태 저장 후 예약 정보 반환
    @POST("/api/reservation/save")
    fun postReservationSaveResponse(
            @Header("Content-Type") content_type : String,
            @Header("jwt") jwt : String?,
            @Body() body: JsonObject
    ) : Call<ReservationSaveResponseData>

    //@@@@@@@@@@@@@@@ store-controller @@@@@@@@@@@@@@@

    // 상가 세부정보 저회
    // - 상가 테이블과 지역 테이블을 조인한 모든 칼럼 반환
    @GET("/api/store/{storeIdx}")
    fun getStoreResponse(
            @Header("jwt") jwt : String?,
            @Path("storeIdx") storeIdx : Int
    ) : Call<StoreResponseData>

    //@@@@@@@@@@@@@@@ reservation-controller @@@@@@@@@@@@@@@
    //TODO: 서버 배포되면 다시 확인하기
    @POST("/api/reservation/cancel")
    fun postReservationCancelResponse(
            @Header("jwt") jwt : String?
    ) : Call<Any>
}