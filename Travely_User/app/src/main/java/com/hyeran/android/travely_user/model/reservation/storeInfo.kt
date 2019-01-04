package com.hyeran.android.travely_user.model.reservation

data class storeInfo(
        var address : String,
        var addressNumber : String,
        var avgLike:Long,
        var closeTime:Long,
        var latitude : Long,
        var openTime : Long,
        var ownerName:String,
        var storeCall : String,
        var storeIdx : Long,
        var storeName : String
)