package com.example.currencytask.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ChangeCurrencyResponseDto(
    @SerializedName("base")
    val base: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("rates")
    val ratesDto: RatesDto?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("timestamp")
    val timestamp: Int?
)