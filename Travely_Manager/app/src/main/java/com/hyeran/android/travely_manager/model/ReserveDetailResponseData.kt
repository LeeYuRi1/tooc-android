package com.hyeran.android.travely_manager.model

data class ReserveDetailResponseData (
        var bagDtoList : ArrayList<BagDtos>,
        var bagImgDtos : ArrayList<BagImgDtos>,
        var depositTime : Long,
        var endTime : Long,
        var overTime : Long,
        var payType : String,
        var price : Long,
        var progressType : String,
        var reserveIdx : Long,
        var startTime : Long,
        var takeTime : Long,
        var userIdx : Long,
        var userImgUrl : String,
        var userName : String,
        var userPhone : String
)