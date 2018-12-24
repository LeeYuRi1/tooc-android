package com.hyeran.android.travely_manager

data class ReserveListTempData (
        var profile : Int,
        var name : String,
        var payment_status : String,
        var price : Int,
        var amount : Int,
        var am_pm : String,
        var hour : Int,
        var minute : Int
)