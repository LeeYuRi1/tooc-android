package com.hyeran.android.travely_user.data

data class ReviewData(
        var reviewer: String,
        var name: String,
        var time: Int,
        var grade: Double,
        var content: String
)