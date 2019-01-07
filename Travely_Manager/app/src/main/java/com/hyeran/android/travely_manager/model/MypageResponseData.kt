package com.hyeran.android.travely_manager.model

data class MypageResponseData (
        var address : String,
        var addressNumber : String,
        var closeTime : Long,
        var onOff : Int,
        var openTime : Long,
        var ownerImgUrl : String,
        var ownerName : String,
        var reviewCount : Int,
        var serviceCount : Int,
        var storeCall : String,
        var storeGrade : Double,
        var storeIdx : Int,
        var storeName : String,
        var storeUrl : String
)

