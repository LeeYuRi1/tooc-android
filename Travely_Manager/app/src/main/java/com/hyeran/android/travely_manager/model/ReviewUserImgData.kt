package com.hyeran.android.travely_manager.model

data class ReviewUserImgData (
        var content : String,
        var createdAt : Long,
        var like : Long,
        var reviewIdx : Long,
        var storeIdx : Long,
        var userIdx : Long,
        var userImgUrl : String,
        var userName : String
)