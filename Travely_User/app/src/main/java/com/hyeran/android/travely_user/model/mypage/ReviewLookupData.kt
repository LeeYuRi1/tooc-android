package com.hyeran.android.travely_user.model.mypage

data class ReviewLookupData(
        var storeImgUrl : String,
        var storeName : String,
        var address : String,
        var content : String,
        var closeTime : Long,
        var createAt : Long,
        var latitude : Double,
        var liked : Long,
        var limit : Long,
        var longitude : Double,
        var openTime : Long,
        var ownerIdx : Long,
        var regionIdx : Long,
        var reviewIdx : Long,
        var storeCall : String,
        var storeIdx : Long,
        var storeUrl : String
)