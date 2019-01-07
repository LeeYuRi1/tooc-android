package com.hyeran.android.tooc.model

data class ProfileResponseData (
        var email : String,
        var favoriteCount : Int,
        var myBagCount : Int,
        var name : String,
        var phone : String,
        var profileImg : String,
        var reviewCount : Int,
        var storeInfoResponseDtoList : ArrayList<StoreInfoResponseData>,
        var userIdx : Int
)