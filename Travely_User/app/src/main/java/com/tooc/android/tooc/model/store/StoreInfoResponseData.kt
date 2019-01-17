package com.tooc.android.tooc.model.store

data class StoreInfoResponseData (
        var address : String,
        var closeTime : Long,
        var limit : Int,
        var latitude : Double,
        var openTime : Long,
        var ownerIdx : Int,
        var storeCall : String,
        var storeIdx : Int,
        var storeImage : String,
        var storeName : String,
        var storeUrl : String,
        var reserveIdx : Int
)