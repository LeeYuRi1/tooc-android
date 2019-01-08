package com.hyeran.android.travely_manager.model

data class ReserveListResponseData (
        var reserveResponseDtoList : ArrayList<ReserveResponseDto>,
        var storeResponseDtoList : ArrayList<StoreResponseDto>
)