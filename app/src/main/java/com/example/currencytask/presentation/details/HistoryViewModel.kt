package com.example.currencytask.presentation.details

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.currencytask.data.repository.CurrencyRepoImpl
import com.example.currencytask.data.repository.HistoryRepoImpl
import com.example.currencytask.domain.repository.CurrencyRepo
import com.example.currencytask.domain.repository.HistoryRepo
import com.example.currencytask.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepo:  HistoryRepoImpl,
    application: Application
) :BaseViewModel(application) {
    fun getData(date:String)=handleFlowResponse {
        historyRepo.getHistoryCurrencyData(date)
    }
}