package com.example.currencytask.domain.repository

import com.example.currencytask.data.remote.dto.ChangeCurrencyResponseDto
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response


interface CurrencyRepo {
    suspend fun getCurrencyData():Response<ChangeCurrencyResponseDto>
}