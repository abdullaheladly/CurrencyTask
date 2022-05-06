package com.example.currencytask.data.repository

import com.example.currencytask.data.remote.CurrencyApi
import com.example.currencytask.data.remote.dto.ChangeCurrencyResponseDto
import com.example.currencytask.domain.repository.CurrencyRepo
import com.example.currencytask.domain.repository.HistoryRepo
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class HistoryRepoImpl @Inject constructor(
    private val currencyApi: CurrencyApi
) :HistoryRepo  {
    override suspend fun getHistoryCurrencyData(date:String): Response<ChangeCurrencyResponseDto> {
        return currencyApi.getHistoricalCurrencyData(date)
    }

}