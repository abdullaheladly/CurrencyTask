package com.example.currencytask.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencytask.data.mapper.toRates
import com.example.currencytask.databinding.FragmentDetailsBinding
import com.example.currencytask.presentation.convertingfragment.CurrenciesAdapter
import com.example.currencytask.domain.model.CustomRateClass
import com.example.currencytask.domain.model.Rates
import com.example.currencytask.util.ApiStatus
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.full.memberProperties


@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding:FragmentDetailsBinding?=null
    private lateinit var   viewModel: HistoryViewModel

    private val binding get() = _binding!!
    private val args by navArgs<DetailsFragmentArgs>()
    private  val currencies:ArrayList<CustomRateClass> =ArrayList()
    private val currenciesAdapter by lazy { CurrenciesAdapter() }

    private lateinit var fromCurrencyKey:String
    private lateinit var toCurrencyKey:String
    private lateinit var rates:Rates
    private lateinit var yesterdayRates:Rates
    private lateinit var theDayBeforeRates:Rates
    private lateinit var earlierRates:Rates
    private lateinit var yesterdayDate:String
    private lateinit var theDayBeforeDate:String
    private lateinit var earlierDate:String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentDetailsBinding.inflate(layoutInflater,container,false)
        viewModel= ViewModelProvider(this).get(HistoryViewModel::class.java)
        getPassedArguments()
        getOtherCurrencies()
        setupRecycleView()
        getCurrentDate()
        getYesterdayValues()
        getTheDayBeforeValues()
        getEarlierValues()
        return binding.root
    }

    private fun setupRecycleView() {
        binding.rvAllCurrencies.adapter=currenciesAdapter
        binding.rvAllCurrencies.layoutManager= LinearLayoutManager(requireContext())
    }

    private fun getOtherCurrencies() {
        var fromCurrency = 0.0
        var toCurrency = 0.0

        if (fromCurrencyKey == "EUR") {
            for (prop in Rates::class.memberProperties) {
                val customRateClass =
                    CustomRateClass(prop.name.toUpperCase(), prop.get(rates).toString())
                currencies.add(customRateClass)
            }
        } else {
            for (prop in Rates::class.memberProperties) {
                if (prop.name.toUpperCase() == fromCurrencyKey) {
                    fromCurrency = prop.get(rates) as Double
                    break
                }
            }
            for (prop in Rates::class.memberProperties) {
                val customRateClass = CustomRateClass(null, null)
                customRateClass.key=prop.name
                toCurrency = prop.get(rates) as Double
                var value = toCurrency / fromCurrency
                customRateClass.value=value.toString()
                currencies.add(customRateClass)
            }
        }

    }


    private fun getEarlierValues() {
        viewModel.getData(earlierDate).observe(requireActivity()) {
            when (it.status) {
                ApiStatus.SUCCESS -> {
                    if (it.data?.success == true){
                    earlierRates= it.data.ratesDto?.toRates()!!
                    setEarlierValue(earlierRates)}else
                    {
                        Toast.makeText(requireContext(), it.data?.error?.info, Toast.LENGTH_SHORT).show()

                    }

                }
                ApiStatus.ERROR -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()

                }
                ApiStatus.LOADING -> {

                }
            }
        }
    }

    private fun getTheDayBeforeValues() {
        viewModel.getData(theDayBeforeDate).observe(requireActivity()) {
            when (it.status) {
                ApiStatus.SUCCESS -> {
                    if (it.data?.success == true){
                        theDayBeforeRates= it.data?.ratesDto?.toRates()!!
                    setTheDayBeforeValue(theDayBeforeRates)}
                    else
                    {
                        Toast.makeText(requireContext(), it.data?.error?.info, Toast.LENGTH_SHORT).show()

                    }

                }
                ApiStatus.ERROR -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()

                }
                ApiStatus.LOADING -> {

                }
            }
        }
    }

    private fun getYesterdayValues() {
        viewModel.getData(yesterdayDate).observe(requireActivity()) {
            when (it.status) {
                ApiStatus.SUCCESS -> {
                    if (it.data?.success == true){

                        yesterdayRates= it.data?.ratesDto?.toRates()!!
                    setYesterdayValue(yesterdayRates)}
                    else
                    {
                        Toast.makeText(requireContext(), it.data?.error?.info, Toast.LENGTH_SHORT).show()

                    }

                }
                ApiStatus.ERROR -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()

                }
                ApiStatus.LOADING -> {

                }
            }
        }
    }



    private fun getCurrentDate() {
        val c = Calendar.getInstance()

        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH).toString()
        var day = c.get(Calendar.DAY_OF_MONTH).toString()
        var yesterday=day.toInt()-1
        var theDayBefore=day.toInt()-2
        var erlier=day.toInt()-3
        if (month.toInt()/10<1){
            month="0$month"
        }
        if (yesterday/10>1||yesterday/10==1){
            yesterdayDate="$year-$month-$yesterday"
        }else{
            yesterdayDate="$year-$month-0$yesterday"
        }

        if (theDayBefore/10>1||theDayBefore/10==1){
            theDayBeforeDate="$year-$month-$theDayBefore"
        }else{
            theDayBeforeDate="$year-$month-0$theDayBefore"
        }

        if (erlier/10>1||erlier/10==1){
            earlierDate="$year-$month-$erlier"
        }else{
            earlierDate="$year-$month-0$erlier"
        }
    }

    private fun getPassedArguments() {
        fromCurrencyKey=args.from
        toCurrencyKey=args.to
        rates=args.rates
    }

    private fun setYesterdayValue(calculateRates:Rates) {
        var fromCurrency=0.0
        var toCurrency=0.0

        for (prop in Rates::class.memberProperties) {
            if (prop.name.toUpperCase()==toCurrencyKey&&fromCurrencyKey=="EUR"){
                toCurrency= prop.get(calculateRates) as Double
                binding.yesterdayValue.text = toCurrency.toString()
                break
            }else{
                if (prop.name.toUpperCase()==fromCurrencyKey){
                    fromCurrency= prop.get(calculateRates) as Double
                }

                if (prop.name.toUpperCase()==toCurrencyKey){
                    toCurrency= prop.get(calculateRates) as Double
                }

            }
        }
        if (fromCurrency!=0.0&&toCurrency!=0.0){
            var value=toCurrency/fromCurrency
            binding.yesterdayValue.text = value.toString()
        }
    }
    private fun setTheDayBeforeValue(calculateRates:Rates) {
        var fromCurrency=0.0
        var toCurrency=0.0

        for (prop in Rates::class.memberProperties) {
            if (prop.name.toUpperCase()==toCurrencyKey&&fromCurrencyKey=="EUR"){
                toCurrency= prop.get(calculateRates) as Double
                binding.theDayBeforeValue.text = toCurrency.toString()
                break
            }else{
                if (prop.name.toUpperCase()==fromCurrencyKey){
                    fromCurrency= prop.get(calculateRates) as Double
                }

                if (prop.name.toUpperCase()==toCurrencyKey){
                    toCurrency= prop.get(calculateRates) as Double
                }

            }
        }
        if (fromCurrency!=0.0&&toCurrency!=0.0){
            var value=toCurrency/fromCurrency
            binding.theDayBeforeValue.text = value.toString()
        }
    }
    private fun setEarlierValue(calculateRates:Rates) {
        var fromCurrency=0.0
        var toCurrency=0.0

        for (prop in Rates::class.memberProperties) {
            if (prop.name.toUpperCase()==toCurrencyKey&&fromCurrencyKey=="EUR"){
                toCurrency= prop.get(calculateRates) as Double
                binding.earlierValue.text = toCurrency.toString()
                break
            }else{
                if (prop.name.toUpperCase()==fromCurrencyKey){
                    fromCurrency= prop.get(calculateRates) as Double
                }

                if (prop.name.toUpperCase()==toCurrencyKey){
                    toCurrency= prop.get(calculateRates) as Double
                }

            }
        }
        if (fromCurrency!=0.0&&toCurrency!=0.0){
            var value=toCurrency/fromCurrency
            binding.earlierValue.text = value.toString()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}