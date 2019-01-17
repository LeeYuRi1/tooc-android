package com.tooc.android.tooc.model.reservation

data class StoreInfoResponseData(
        var address : String,
        var addressNumber : String,
        var avgLike:Double,
        var closeTime:Long,
        var openTime : Long,
        var ownerName:String,
        var storeCall : String,
        var storeIdx : Int,
        var storeName : String,
        var latitude : Double,
        var longitude : Double
)