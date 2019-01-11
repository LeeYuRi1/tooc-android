package com.tooc.android.tooc.model.store

data class ReviewResponseData (
        var content : String,
        var createdAt : Long,
        var like : Double,
        var reviewIdx : Int,
        var storeIdx : Int,
        var userIdx : Int,
        var userName : String
)