package com.tooc.android.tooc.model.mypage

data class SimpleStoreResponseDtosData (
        var address : String,
        var closeTime : Long,
        var grade : Float,
        var openTime : Long,
        var storeIdx : Int,
        var storeImgUrl : String,
        var storeName : String
)