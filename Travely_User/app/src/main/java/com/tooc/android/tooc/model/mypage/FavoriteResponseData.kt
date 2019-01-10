package com.tooc.android.tooc.model.mypage

data class FavoriteResponseData (
        var regionIdx : Int,
        var regionName : String,
        var simpleStoreResponseDtos : ArrayList<SimpleStoreResponseDtosData>
)