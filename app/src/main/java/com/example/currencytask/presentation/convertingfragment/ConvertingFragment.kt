package com.example.currencytask.presentation.convertingfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.currencytask.R
import com.example.currencytask.databinding.FragmentConvertingBinding


class ConvertingFragment : Fragment() {
    private var _binding:FragmentConvertingBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding= FragmentConvertingBinding.inflate(layoutInflater,container,false)



        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}