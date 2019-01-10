package com.tooc.android.tooc.model.mypage

data class ReviewSaveResponseData (
        var storeIdx : Int,
        var content : String,
        var liked : Long
)