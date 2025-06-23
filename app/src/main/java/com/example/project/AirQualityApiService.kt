package com.example.project

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityApiService {
    @GET("getMsrstnAcctoRltmMesureDnsty")
    fun getAirQuality(
        @Query("serviceKey") serviceKey: String,
        @Query("stationName") stationName: String,
        @Query("dataTerm") dataTerm: String,
        @Query("returnType") returnType: String,
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int
    ): Call<AirQualityResponse>
}