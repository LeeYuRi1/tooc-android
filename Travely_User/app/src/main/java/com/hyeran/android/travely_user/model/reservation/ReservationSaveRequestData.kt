package com.hyeran.android.travely_user.model.reservation

data class ReservationSaveRequestData(
        var storeIdx : Long,
        var startTime : Long,
        var endTime : Long,
        var bagDtos : ArrayList<bagInfo>,
        var payType : String
)