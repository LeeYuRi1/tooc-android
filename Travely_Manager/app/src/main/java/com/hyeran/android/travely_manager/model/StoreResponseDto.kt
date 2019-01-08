package com.hyeran.android.travely_manager.model

data class StoreResponseDto (
    var bagDtos: ArrayList<BagDtos>,
    var endTime : Long,
    var price : Long,
    var progressType : String,
    var reserveIdx : Long,
    var startTime : Long,
    var stateType : String,
    var userIdx : Long,
    var userImg : String,
    var userName : String
)