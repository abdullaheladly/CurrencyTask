package com.example.currencytask.presentation.convertingfragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.currencytask.data.mapper.toRates
import com.example.currencytask.databinding.FragmentConvertingBinding
import com.example.currencytask.domain.model.Rates
import com.example.currencytask.presentation.TestViewModel
import com.example.currencytask.util.ApiStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.reflect.full.memberProperties


@AndroidEntryPoint
class ConvertingFragment : Fragment() {
    private lateinit var   viewModel: TestViewModel
    private var _binding:FragmentConvertingBinding?=null
    private val binding get() = _binding!!
    private lateinit var rates: Rates
    private lateinit var fromCurrencyKEY:String
    private lateinit  var toCurrencyKEY:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel= ViewModelProvider(this).get(TestViewModel::class.java)


        _binding= FragmentConvertingBinding.inflate(layoutInflater,container,false)
        getData()






        return binding.root
    }

    private fun getData() {
        viewModel.getData().observe(requireActivity()) {
            when (it.status) {
                ApiStatus.SUCCESS -> {
                    rates= it.data?.ratesDto?.toRates()!!
                    setSpinners()
                    setListeners()
                }
                ApiStatus.ERROR -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()

                }
                ApiStatus.LOADING -> {

                }
            }
        }
    }

    private fun setListeners() {
        binding.edtFrom.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                getValues(fromCurrencyKEY,toCurrencyKEY)

            }
        })

    }

    private fun setSpinners() {
        val names :ArrayList<String> = arrayListOf()
        Rates::class.memberProperties.map { itm->
            names.add(itm.name.toString().toUpperCase())
        }
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, names)
        binding.fromSpinner.adapter=adapter
        binding.toSpinner.adapter=adapter
        binding.fromSpinner.setSelection(46)
        binding.fromSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                fromCurrencyKEY=binding.fromSpinner.selectedItem.toString()
                toCurrencyKEY=binding.toSpinner.selectedItem.toString()
                getValues(fromCurrencyKEY,toCurrencyKEY)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        binding.toSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                fromCurrencyKEY=binding.fromSpinner.selectedItem.toString()
                toCurrencyKEY=binding.toSpinner.selectedItem.toString()
                getValues(fromCurrencyKEY,toCurrencyKEY)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun getValues(from: String, to: String) {
        var fromCurrency=0.0
        var toCurrency=0.0

        for (prop in Rates::class.memberProperties) {
            if (prop.name.toUpperCase()==to&&binding.fromSpinner.selectedItem=="EUR"&&binding.edtFrom.text.toString()=="1"){
                toCurrency= prop.get(rates) as Double
                binding.edtTo.setText(toCurrency.toString())
                break
            }else if (prop.name.toUpperCase()==to&&binding.fromSpinner.selectedItem=="EUR"&&!binding.edtFrom.text.isNullOrEmpty()){
                toCurrency= prop.get(rates) as Double
                val newValue=toCurrency*binding.edtFrom.text.toString().toInt()
                binding.edtTo.setText(newValue.toString())
                break
            }else{
                if (prop.name.toUpperCase()==from){
                    fromCurrency= prop.get(rates) as Double
                }

                if (prop.name.toUpperCase()==to){
                    toCurrency= prop.get(rates) as Double
                }

            }
        }
        if (fromCurrency!=0.0&&toCurrency!=0.0&&!binding.edtFrom.text.isNullOrEmpty()){
            var value=toCurrency/fromCurrency
            if (binding.edtFrom.text.toString()=="1") {
                binding.edtTo.setText(value.toString())
            }else{
                value*=binding.edtFrom.text.toString().toInt()
                binding.edtTo.setText(value.toString())
            }

        }


    }


    override fun onDestroyView() {

        super.onDestroyView()
        _binding=null
    }







}