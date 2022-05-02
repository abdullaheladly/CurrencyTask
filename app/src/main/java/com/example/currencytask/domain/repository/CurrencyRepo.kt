package com.example.currencytask.domain.repository

import com.example.currencytask.data.remote.dto.ChangeCurrencyResponseDto
import retrofit2.Response



interface CurrencyRepo {
    suspend fun getCurrencyData():Response<ChangeCurrencyResponseDto>
}