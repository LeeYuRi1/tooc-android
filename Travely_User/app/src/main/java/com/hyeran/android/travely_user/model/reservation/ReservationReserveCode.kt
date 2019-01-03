package com.hyeran.android.travely_user.model.reservation

data class ReservationReserveCode(
        var bagDtos : ArrayList<bagDtosData>,
        var bagImgDtos : ArrayList<bagImgDtos>,
        var depositTime : Long,
        var endTime : Long,
        var extraCharge : Long,
        var extraChargeCount : Long,
        var payType : String,
        var price : Long,
        var priceIdx : Long,
        var priceUnit : Long,
        var progressType : String,
        var startTime : Long,
        var stateType : String,
        var store : storeInfo,
        var takeTime : Long
)