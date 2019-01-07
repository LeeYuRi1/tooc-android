package com.hyeran.android.tooc.model.region

data class RegionResponseData(
        var regionIdx: Int,
        var regionName: String,
        var simpleStoreResponseDtos : ArrayList<SimpleStoreResponseData>
)