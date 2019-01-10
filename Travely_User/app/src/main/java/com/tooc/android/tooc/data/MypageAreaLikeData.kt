package com.tooc.android.tooc.data

data class MypageAreaLikeData (
        var name : String,
        var num : Int,
        var rvlike : ArrayList<MypageLikeData>? = null
)