package com.hyeran.android.travely_user.model

data class ReservationSaveResponseData(
        var bagDtos: ArrayList<bagData>,
        var bagImages: String,
        var endTime: Long,
        var payType: String,
        var price: Int,
        var reserveCode: String,
        var startTime: Long,
        var stateType: String,
        var storeDto: ArrayList<storeData>
)

