package com.example.currencytask.domain.model



data class ChangeCurrencyResponse(

    val base: String?,
    val date: String?,
    val ratesDto: Rates?,
    val success: Boolean?,
    val timestamp: Int?
)