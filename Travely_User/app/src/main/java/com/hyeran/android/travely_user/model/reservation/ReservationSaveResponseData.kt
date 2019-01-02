package com.hyeran.android.travely_user.model.reservation

data class ReservationSaveResponseData(
        var bagResponseDtos: ArrayList<BagResponseData>,
        var bagImages: String,
        var endTime: Long,
        var payType: String,
        var price: Int,
        var reserveCode: String,
        var startTime: Long,
        var stateType: String,
        var storeDto: ArrayList<StoreData>
)
