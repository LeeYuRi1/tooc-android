package com.hyeran.android.travely_user.data

data class MypageAreaLikeData (
        var name : String,
        var num : Int,
        var rvlike : ArrayList<MypageLikeData>? = null
)