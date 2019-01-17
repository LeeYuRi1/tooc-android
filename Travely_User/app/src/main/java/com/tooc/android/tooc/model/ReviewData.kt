package com.tooc.android.tooc.model

data class ReviewData(
        var reviewer: String,
        var name: String,
        var time: Int,
        var grade: Double,
        var content: String
)