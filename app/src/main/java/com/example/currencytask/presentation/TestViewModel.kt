package com.example.currencytask.presentation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.currencytask.data.repository.CurrencyRepoImpl
import com.example.currencytask.domain.repository.CurrencyRepo
import com.example.currencytask.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TestViewModel @Inject constructor(
    private val currencyRepo: CurrencyRepoImpl,
    application: Application
) :BaseViewModel(application) {
    @RequiresApi(Build.VERSION_CODES.M)
    fun getData()=handleFlowResponse {
        currencyRepo.getCurrencyData()
    }
}