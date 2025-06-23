package com.example.project

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {
    @GET("getUltraSrtNcst")
    fun getNowcast(
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRows: Int = 100,
        @Query("pageNo") pageNo: Int = 1,
        @Query("dataType") dataType: String = "JSON", // 이게 핵심
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int,
        @Query("_type") type: String = "json"
    ): Call<WeatherResponse>
}
