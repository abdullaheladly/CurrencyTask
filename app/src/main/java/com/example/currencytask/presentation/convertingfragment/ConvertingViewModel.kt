package com.example.currencytask.presentation.convertingfragment

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.currencytask.data.repository.CurrencyRepoImpl
import com.example.currencytask.domain.repository.CurrencyRepo
import com.example.currencytask.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ConvertingViewModel @Inject constructor(
    private val currencyRepo: CurrencyRepoImpl,
    application: Application
) :BaseViewModel(application) {
    fun getData()=handleFlowResponse {
        currencyRepo.getCurrencyData()
    }
}