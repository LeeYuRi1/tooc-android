package com.hyeran.android.travely_user.model.region

data class RegionResponseData(
        var regionIdx: Int,
        var regionName: String,
        var simpleStoreResponseDtos : ArrayList<SimpleStoreResponseData>
)