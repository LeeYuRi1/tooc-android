package com.hyeran.android.travely_user.model.mypage

data class FavoriteResponseData (
        var regionIdx : Int,
        var regionName : String,
        var simpleStoreResponseDtos : ArrayList<SimpleStoreResponseDtosData>
)