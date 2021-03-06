package com.example.currencytask.domain.repository

import com.example.currencytask.data.remote.dto.ChangeCurrencyResponseDto
import retrofit2.Response

interface HistoryRepo {
    suspend fun getHistoryCurrencyData(date:String): Response<ChangeCurrencyResponseDto>

}