package com.tooc.android.tooc.model.store

data class ReviewResponseData (
        var content : String,
        var createdAt : Double,
        var like : Int,
        var reviewIdx : Int,
        var storeIdx : Int,
        var userIdx : Int,
        var userName : String
)