package com.tooc.android.tooc.model.reservation

data class ReservationSaveRequestData(
        var storeIdx : Long,
        var startTime : Long,
        var endTime : Long,
        var bagDtos : ArrayList<bagInfo>,
        var payType : String
)