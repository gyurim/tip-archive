package com.gyurim.airbnb

import retrofit2.Call
import retrofit2.http.GET

interface HouseService {
    @GET("/v3/3eeef5e0-4972-4089-ade0-d496d3402dda")
    fun getHouseList(): Call<HouseDto>
}