package com.hyeran.android.travely_user.model

data class ReviewResponseData (
        var content : String,
        var createdAt : Double,
        var like : Int,
        var reviewIdx : Int,
        var storeIdx : Int,
        var userIdx : Int
)