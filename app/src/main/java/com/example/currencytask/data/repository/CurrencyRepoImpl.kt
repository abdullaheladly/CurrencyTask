package com.example.currencytask.data.repository

import com.example.currencytask.data.remote.CurrencyApi
import com.example.currencytask.data.remote.dto.ChangeCurrencyResponseDto
import com.example.currencytask.domain.repository.CurrencyRepo
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class CurrencyRepoImpl @Inject constructor(
    private val currencyApi: CurrencyApi
) :CurrencyRepo  {
    override suspend fun getCurrencyData(): Response<ChangeCurrencyResponseDto> {
        return currencyApi.getCurrenciesData()
    }
}