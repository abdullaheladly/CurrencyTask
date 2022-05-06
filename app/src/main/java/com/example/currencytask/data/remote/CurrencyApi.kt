package com.example.currencytask.data.remote

import com.example.currencytask.data.remote.dto.ChangeCurrencyResponseDto
import com.example.currencytask.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    suspend fun getCurrenciesData(
        @Query("access_key") access_key:String=API_KEY
    ):Response<ChangeCurrencyResponseDto>

    @GET("{date}")
    suspend fun getHistoricalCurrencyData(
        @Path("date") date:String,
        @Query("access_key") access_key:String=API_KEY
    ):Response<ChangeCurrencyResponseDto>




}