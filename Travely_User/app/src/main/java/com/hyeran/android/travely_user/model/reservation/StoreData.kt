package com.hyeran.android.travely_user.model.reservation


data class StoreData(
        var address : String,
        var avgLike: Int,
        var closeTime : String,
        var latitude : Int,
        var longitude : Int,
        var openTime : String,
        var ownerName : String,
        var storeCall : String,
        var storeIdx : Int,
        var storeName : String
)